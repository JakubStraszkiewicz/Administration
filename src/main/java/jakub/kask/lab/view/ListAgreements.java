package jakub.kask.lab.view;

import jakub.kask.lab.business.AgreementType;
import jakub.kask.lab.business.boundary.EmployeeService;
import jakub.kask.lab.business.entities.Agreement;
import jakub.kask.lab.business.entities.User;

import javax.ejb.EJB;
import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Collection;

@Named
@ViewScoped
public class ListAgreements implements Serializable {
    @EJB
    private EmployeeService employeeService;

    @Inject
    AuthContext authContext;

    private Collection<Agreement> agreements;

    public Collection<Agreement> getAgreements() {
        return agreements != null ? agreements : (agreements = employeeService.findAllAgreements());
    }

    public Collection<Agreement> getAgreementByType(ValueChangeEvent e) {
        AgreementType a = (AgreementType) e.getNewValue();
        return agreements = employeeService.findAgreementsByType(a);
    }

    public String removeAgreement(Agreement agreement) {
        this.employeeService.removeAgreement(agreement);
        return "list_agreements?faces-redirect=true";
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
