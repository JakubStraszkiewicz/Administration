package jakub.kask.lab.view;

import jakub.kask.lab.business.boundary.EmployeeService;
import jakub.kask.lab.business.entities.Employee;
import jakub.kask.lab.business.entities.Income;
import lombok.Getter;
import lombok.Setter;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Collection;

@Named
@ViewScoped
public class EditIncome implements Serializable {

    @Inject
    private EmployeeService employeeService;

    @Inject
    AuthContext authContext;

    @Setter
    @Getter
    private Income income = new Income();

    public Collection<Employee> getAvailableEmployees() {
        return employeeService.findAllEmployees();
    }

    public String saveIncome() {
        income.setOwner(authContext.getCurrentUser());
        employeeService.saveIncome(income);
        return "list_incomes?faces-redirect=true";
    }
}
