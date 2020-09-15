package jakub.kask.lab.business.boundary;

import jakub.kask.lab.business.entities.Employee;
import jakub.kask.lab.business.entities.Income;
import jakub.kask.lab.business.entities.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeServiceTest {

    @Mock
    EntityManager em;

    @Before
    public void init() {
        this.em = mock(EntityManager.class);
    }

    @Test
    public void findAllEmployeesIfMoreThenOne() {
        Employee employee1 = new Employee("01234567890","Jakub","Straszkiewicz",null);
        Employee employee2 = new Employee("1","Jakub","Straszkiewicz",null);

        employee1.setId(0);
        employee2.setId(1);

        List<Employee> employeeList = new ArrayList<>();
        employeeList.add(employee1);
        employeeList.add(employee2);

        TypedQuery<Employee> mockedQuery = mock(TypedQuery.class);
        when(mockedQuery.getResultList()).thenReturn(employeeList);
        when(this.em.createNamedQuery(Employee.Queries.FIND_ALL,Employee.class)).thenReturn(mockedQuery);

        EmployeeService employeeService = new EmployeeService();
        employeeService.em = this.em;

        Collection<Employee> employeeCollection = employeeService.findAllEmployees();

        assertEquals(2,employeeCollection.size());
    }

    @Test
    public void findAllEmployeesIfNoneInDatabase() {

        TypedQuery<Employee> mockedQuery = mock(TypedQuery.class);
        when(this.em.createNamedQuery(Employee.Queries.FIND_ALL,Employee.class)).thenReturn(mockedQuery);

        EmployeeService employeeService = new EmployeeService();
        employeeService.em = this.em;

        Collection<Employee> employeeCollection = employeeService.findAllEmployees();

        assertEquals(0 ,employeeCollection.size());
    }


    @Test
    public void removeEmployeeIfDoesNotExist() {

        EmployeeService employeeService = new EmployeeService();
        employeeService.em = this.em;

        Employee employee = new Employee("01234567890","asd","Asd",null);
        employee.setId(1);

        Income income = new Income(0,0, Date.valueOf("2017-05-05"),employee,new User());
        income.setId(0);

        List<Income> incomeList = new ArrayList<>();
        incomeList.add(income);

        TypedQuery<Income> mockedQuery = mock(TypedQuery.class);
        when(mockedQuery.getResultList()).thenReturn(incomeList);
        when(this.em.createNamedQuery(Income.Queries.FIND_ALL,Income.class)).thenReturn(mockedQuery);

        employeeService.removeEmployee(new Employee());

    }
}