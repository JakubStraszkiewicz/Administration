package jakub.kask.lab.view;

import jakub.kask.lab.business.boundary.EmployeeService;
import jakub.kask.lab.business.entities.Employee;
import jakub.kask.lab.business.entities.User;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Collection;

@Named
@RequestScoped
public class ListEmployees implements Serializable {

    @Inject
    private AuthContext authContext;

    @EJB
    private EmployeeService employeeService;

    private Collection<Employee> employees;

    public Collection<Employee> getEmployees() {
        return employees != null ? employees : (employees = employeeService.findAllEmployees());
    }

    public String removeEmployee(Employee employee) {
        employeeService.removeEmployee(employee);
        return "list_employees?faces-redirect=true";
    }

    public boolean canAdd() {
        return authContext.isUserInRole(User.Roles.ADMINISTRATION);
    }

    public boolean canRemove() {
        return authContext.isUserInRole(User.Roles.ADMINISTRATION);
    }

    public boolean canEdit() {
        return authContext.isUserInRole(User.Roles.ADMINISTRATION);
    }
}
