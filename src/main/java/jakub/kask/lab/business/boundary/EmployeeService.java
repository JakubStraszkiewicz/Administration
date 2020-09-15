package jakub.kask.lab.business.boundary;

import jakub.kask.lab.business.AgreementType;
import jakub.kask.lab.business.auth.Authorize;
import jakub.kask.lab.business.entities.*;
import jakub.kask.lab.business.events.*;

import javax.annotation.security.PermitAll;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.*;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Stateless
@PermitAll
@Authorize
public class EmployeeService implements Serializable {

    @PersistenceContext
    EntityManager em;

    @Inject
    Event<IncomeEvent> incomeEvent;

    public Collection<Employee> findAllEmployees() {
        TypedQuery<Employee> query = em.createNamedQuery(
                Employee.Queries.FIND_ALL, Employee.class);
                query.setHint("javax.persistence.loadgraph",
                em.getEntityGraph(Employee.Graphs.WITH_AGREEMENTS));
        return query.getResultList();

        //TypedQuery<Employee> query = em.createNamedQuery(Employee.Queries.FIND_ALL, Employee.class);
        //return query.getResultList();
    }

    public Employee findEmployee(int id) {
        return em.find(Employee.class, id);
    }

    @Transactional
    public void removeEmployee(Employee employee) {

        for (Income income : findAllIncomes()) {
            if (income.getEmployee().getId().equals(employee.getId())) {
                removeIncome(income);
            }
        }

        employee = em.merge(employee);
        em.remove(employee);
    }

    @Transactional
    public Employee saveEmployee(Employee employee) {
        if (employee.getId() == null) {
            em.persist(employee);
        } else {
            employee = em.merge(employee);
        }

        return employee;
    }

    public Collection<Agreement> findAllAgreements() {
        TypedQuery<Agreement> query = em.createNamedQuery(Agreement.Queries.FIND_ALL, Agreement.class);
        return query.getResultList();
    }

    public Collection<Agreement> findAllAvailableAgreements(){
        TypedQuery<Agreement> query = em.createNamedQuery(Agreement.Queries.FIND_ALL_UNASSIGNED, Agreement.class);
        return query.getResultList();
    }

    public Agreement findAgreement(int id) {
        return em.find(Agreement.class, id);
    }

    public Collection<Agreement> findAgreementsByType(AgreementType agreementType) {
        if(agreementType == null) {
            return findAllAgreements();
        } else {
            TypedQuery<Agreement> query = em.createNamedQuery(Agreement.Queries.FIND_BY_TYPE, Agreement.class);
            query.setParameter("agreementType", agreementType);
            return query.getResultList();
        }
    }

    @Transactional
    public void removeAgreement(Agreement agreement) {

        for(Employee employee : findAllEmployees()){
            employee.getAgreements().remove(agreement);
        }

        agreement = em.merge(agreement);
        em.remove(agreement);
    }

    @Transactional
    public Agreement saveAgreement(Agreement agreement) {
        if (agreement.getId() == null) {
            em.persist(agreement);
        } else {
            agreement = em.merge(agreement);
        }

        return agreement;
    }

    public Collection<Income> findAllIncomes() {

        TypedQuery<Income> query = em.createNamedQuery(
                Income.Queries.FIND_ALL, Income.class);
                query.setHint("javax.persistence.loadgraph",
                em.getEntityGraph(Income.Graphs.WITH_EMPLOYEE));
        return query.getResultList();

        //TypedQuery<Income> query = em.createNamedQuery(Income.Queries.FIND_ALL, Income.class);
        //return query.getResultList();
    }

    public Income findIncome(int id) {
        return em.find(Income.class, id);
    }

    public List<Income> findRecentIncomes() {
        TypedQuery<Income> query = em.createNamedQuery(Income.Queries.FIND_MOST_RECENT, Income.class);
        return query.getResultList();
    }

    @Transactional
    public void removeIncome(Income income) {
        income = em.merge(income);
        em.remove(income);
        incomeEvent.select(IncomeDeletion.Literal).fire(IncomeEvent.of(income));
    }

    @Transactional
    public Income saveIncome(Income income) {
        if (income.getId() == null) {
            em.persist(income);
            incomeEvent.select(IncomeCreation.Literal).fire(IncomeEvent.of(income));
        } else {
            income = em.merge(income);
            incomeEvent.select(IncomeModification.Literal).fire(IncomeEvent.of(income));
        }

        return income;
    }

    public List<Income> filter(String attribute, String filterString) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Income> query = builder.createQuery(Income.class);
        Root<Income> income = query.from(Income.class);
        query.select(income);


        Predicate predicate = null;
        switch(attribute)
        {
            case Income_.ID:
                predicate = builder.like(
                        builder.lower(income.get(Income_.id).as(String.class)),
                        "%" + filterString.toLowerCase() + "%");
                break;
            case Income_.SALARY:
                predicate = builder.like(
                        builder.lower(income.get(Income_.salary).as(String.class)),
                        "%" + filterString.toLowerCase() + "%");
                break;
            case Income_.ALLOWANCE:
                predicate = builder.like(
                        builder.lower(income.get(Income_.allowance).as(String.class)),
                        "%" + filterString.toLowerCase() + "%");
                break;
            case Employee_.SURNAME:
                Join<Income, Employee> joinEmployees = income.join(Income_.employee, JoinType.LEFT);

                Expression<String> surname = builder.concat(joinEmployees.get(Employee_.surname), " ");
                Expression<String> fullname = builder.concat(surname,joinEmployees.get(Employee_.name));

                predicate = builder.like(
                        builder.lower(fullname),
                        "%" + filterString.toLowerCase() + "%");
                break;
            case Income_.CREATION_DATE:
                predicate = builder.like(
                        builder.lower(income.get(Income_.creationDate).as(String.class)),
                        "%" + filterString.toLowerCase() + "%");
                break;
            case Income_.MODIFICATION_DATE:
                predicate = builder.like(
                        builder.lower(income.get(Income_.creationDate).as(String.class)),
                        "%" + filterString.toLowerCase() + "%");
                break;

        }

        query.where(predicate);
        return em.createQuery(query).getResultList();
    }

    public List<Income> sort(String attribute, boolean sortAsc) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Income> query = builder.createQuery(Income.class);
        Root<Income> income = query.from(Income.class);
        query.select(income);

        Path<?> expression = null;
        switch(attribute)
        {
            case Income_.ID:
                expression = income.get(Income_.id);
                break;
            case Income_.SALARY:
                expression = income.get(Income_.salary);
                break;
            case Income_.ALLOWANCE:
                expression = income.get(Income_.allowance);
                break;
            case Employee_.SURNAME:
                Join<Income, Employee> joinEmployees = income.join(Income_.employee, JoinType.LEFT);
                expression = joinEmployees.get(Employee_.surname);
                Order orderJoin = orderExpression(builder,expression,sortAsc);
                query.orderBy(orderJoin);
                return em.createQuery(query).getResultList();
            case Income_.CREATION_DATE:
                expression = income.get(Income_.creationDate);
                break;
            case Income_.MODIFICATION_DATE:
                expression = income.get(Income_.modificationDate);
                break;
        }

        Order order = orderExpression(builder,expression,sortAsc);

        query.orderBy(order);
        return em.createQuery(query).getResultList();
    }

    private Order orderExpression(CriteriaBuilder builder, Path<?> expression, boolean sortAsc) {
        Order order = null;
        if(sortAsc) {
            order = builder.asc(expression);
        } else {
            order = builder.desc(expression);
        }
        return order;
    }



}
