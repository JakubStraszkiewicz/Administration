package jakub.kask.lab.view;

import jakub.kask.lab.business.boundary.EmployeeService;
import jakub.kask.lab.business.entities.Income;
import jakub.kask.lab.business.entities.User;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Collection;

@Named
@RequestScoped
public class ListIncomes implements Serializable {
    @Inject
    private EmployeeService employeeService;

    @Inject
    private AuthContext authContext;

    private Collection<Income> incomes;

    public Collection<Income> getIncomes() {
        return incomes != null ? incomes : (incomes = employeeService.findAllIncomes());
    }

    public String removeIncome(Income income) {
        this.employeeService.removeIncome(income);
        return "list_incomes?faces-redirect=true";
    }

    public boolean canAdd() {
        return authContext.isAuthenticated();
    }

    public boolean canRemove(Income income) {
        return authContext.isUserInRole(User.Roles.ADMINISTRATION) || income.getOwner().equals(authContext.getCurrentUser());
    }

    public boolean canEdit(Income income) {
        return authContext.isUserInRole(User.Roles.ADMINISTRATION) || income.getOwner().equals(authContext.getCurrentUser());
    }
}
