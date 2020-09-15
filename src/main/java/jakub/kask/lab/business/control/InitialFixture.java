package jakub.kask.lab.business.control;

import jakub.kask.lab.business.AgreementType;
import jakub.kask.lab.business.auth.CryptUtils;
import jakub.kask.lab.business.entities.*;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static java.util.Arrays.asList;

@ApplicationScoped
public class InitialFixture {

    @PersistenceContext
    EntityManager em;

    @Transactional
    public void init(@Observes @Initialized(ApplicationScoped.class) Object init) {


        List<User> users = asList(
                new User("admin", CryptUtils.sha256("admin"), asList(User.Roles.ADMINISTRATION,User.Roles.USER)),
                new User("user1", CryptUtils.sha256("p@ss1"), asList(User.Roles.USER)),
                new User("user2", CryptUtils.sha256("p@ss2"), asList(User.Roles.USER))
        );

        Calendar start1 = Calendar.getInstance();
        start1.set(1996, Calendar.JANUARY, 24);
        Calendar end1 = Calendar.getInstance();
        end1.set(2018, Calendar.FEBRUARY, 7);
        Agreement agreement1 = new Agreement(AgreementType.contractOfEmployment, start1.getTime(), end1.getTime());

        Calendar start2 = Calendar.getInstance();
        start2.set(1995, Calendar.APRIL, 2);
        Calendar end2 = Calendar.getInstance();
        end2.set(2017, Calendar.DECEMBER, 5);
        Agreement agreement2 = new Agreement(AgreementType.ContractOfWork, start2.getTime(), end2.getTime());

        Employee employee1 = new Employee("96084825412",
                "Tomasz", "Nowak", asList(agreement1, agreement2));

        Calendar incomeDate = Calendar.getInstance();
        Income income1 = new Income(1500,15,incomeDate.getTime(),employee1,users.get(1));

        users.forEach(user -> em.persist(user));
        em.flush();

        em.persist(agreement1);
        em.persist(agreement2);
        em.persist(employee1);
        em.persist(income1);

        List<Permission> permissions = asList(
                new Permission(User.Roles.USER,"saveIncome","IF_OWNER"),
                new Permission(User.Roles.ADMINISTRATION, "saveIncome", "GRANTED"),
                new Permission(User.Roles.USER,"removeIncome","IF_OWNER"),
                new Permission(User.Roles.ADMINISTRATION, "removeIncome", "GRANTED"),
                new Permission(User.Roles.USER,"findAgreement","DENIED"),
                new Permission(User.Roles.ADMINISTRATION, "findAgreement", "GRANTED"),
                new Permission(User.Roles.USER,"saveAgreement","DENIED"),
                new Permission(User.Roles.ADMINISTRATION, "saveAgreement", "GRANTED"),
                new Permission(User.Roles.USER,"removeAgreement","DENIED"),
                new Permission(User.Roles.ADMINISTRATION, "removeAgreement", "GRANTED"),
                new Permission(User.Roles.USER,"saveEmployee","DENIED"),
                new Permission(User.Roles.ADMINISTRATION, "saveEmployee", "GRANTED"),
                new Permission(User.Roles.USER,"removeEmployee","DENIED"),
                new Permission(User.Roles.ADMINISTRATION, "removeEmployee", "GRANTED"),
                new Permission(User.Roles.USER,"findRecentIncomes","GRANTED"),
                new Permission(User.Roles.ADMINISTRATION, "findRecentIncomes", "GRANTED"),
                new Permission(User.Roles.USER,"findIncome","GRANTED"),
                new Permission(User.Roles.ADMINISTRATION, "findIncome", "GRANTED"),
                new Permission(User.Roles.USER,"findAllIncomes","GRANTED"),
                new Permission(User.Roles.ADMINISTRATION, "findAllIncomes", "GRANTED"),
                new Permission(User.Roles.USER,"findAgreementsByType","GRANTED"),
                new Permission(User.Roles.ADMINISTRATION, "findAgreementsByType", "GRANTED"),
                new Permission(User.Roles.USER,"findAllAvailableAgreements","GRANTED"),
                new Permission(User.Roles.ADMINISTRATION, "findAllAvailableAgreements", "GRANTED"),
                new Permission(User.Roles.USER,"findAllAgreements","GRANTED"),
                new Permission(User.Roles.ADMINISTRATION, "findAllAgreements", "GRANTED"),
                new Permission(User.Roles.USER,"findEmployee","GRANTED"),
                new Permission(User.Roles.ADMINISTRATION, "findEmployee", "GRANTED"),
                new Permission(User.Roles.USER,"findAllEmployees","GRANTED"),
                new Permission(User.Roles.ADMINISTRATION, "findAllEmployees", "GRANTED"),
                new Permission(User.Roles.USER,"findAllUsers","DENIED"),
                new Permission(User.Roles.ADMINISTRATION, "findAllUsers", "GRANTED"),
                new Permission(User.Roles.USER,"findUser","DENIED"),
                new Permission(User.Roles.ADMINISTRATION, "findUser", "GRANTED"),
                new Permission(User.Roles.USER,"findCurrentUser","GRANTED"),
                new Permission(User.Roles.ADMINISTRATION, "findCurrentUser", "GRANTED"),
                new Permission(User.Roles.USER,"changePassword","GRANTED"),
                new Permission(User.Roles.ADMINISTRATION, "changePassword", "GRANTED"),
                new Permission(User.Roles.USER,"saveUser","DENIED"),
                new Permission(User.Roles.ADMINISTRATION, "saveUser", "GRANTED"),
                new Permission(User.Roles.USER,"findAvailableRoles","DENIED"),
                new Permission(User.Roles.ADMINISTRATION, "findAvailableRoles", "GRANTED"),
                new Permission(User.Roles.USER,"sort","GRANTED"),
                new Permission(User.Roles.ADMINISTRATION, "sort", "GRANTED"),
                new Permission(User.Roles.USER,"filter","GRANTED"),
                new Permission(User.Roles.ADMINISTRATION, "filter", "GRANTED"),
                new Permission("ANONYMOUS","registryUser","GRANTED")
        );
        permissions.forEach(permission -> em.persist(permission));
        em.flush();

        for (int i = 0; i < 50; i++) {
            Employee employee = new Employee(
                    "01234567890",
                    "ul",
                    "as",
                    randomAgreement());

            em.persist(employee);
        }
    }

    private List<Agreement> randomAgreement() {
        List<Agreement> list = new ArrayList<>();
        Agreement agreement = new Agreement(AgreementType.contractOfEmployment, Date.valueOf("2019-05-05"),Date.valueOf("2020-02-02"));
        em.persist(agreement);
        list.add(agreement);
        return list;
    }
}
