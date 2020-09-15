package jakub.kask.lab.view;

import jakub.kask.lab.business.boundary.EmployeeService;
import jakub.kask.lab.business.entities.Agreement;
import jakub.kask.lab.business.entities.Employee;
import lombok.Getter;
import lombok.Setter;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Collection;

@Named
@ViewScoped
public class EditEmployee implements Serializable {

    @Inject
    private EmployeeService employeeService;

    @Getter
    @Setter
    private Employee employee = new Employee();

    public Collection<Agreement> getAvailableAgreements() {
        Collection<Agreement> agreements = employeeService.findAllAvailableAgreements();
        agreements.addAll(employee.getAgreements());

        return agreements;
    }

    public String saveEmployee() {
        employeeService.saveEmployee(employee);
        return "list_employees?faces-redirect=true";
    }
}
