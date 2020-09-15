package jakub.kask.lab.view;

import jakub.kask.lab.business.boundary.EmployeeService;
import jakub.kask.lab.business.entities.Agreement;
import lombok.Getter;
import lombok.Setter;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class EditAgreement implements Serializable {

    @Inject
    private EmployeeService employeeService;

    @Getter
    @Setter
    private Agreement agreement = new Agreement();

    public String saveAgreement() {
        employeeService.saveAgreement(agreement);
        return "list_agreements?faces-redirect=true";
    }
}
