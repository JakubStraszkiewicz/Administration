package jakub.kask.lab.view;

import jakub.kask.lab.business.boundary.EmployeeService;
import jakub.kask.lab.business.entities.Income;
import jakub.kask.lab.business.events.IncomeEvent;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.faces.component.UIComponent;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ApplicationScoped
public class ListRecentReleases implements IncomeSource, Serializable {
    @EJB
    EmployeeService employeeService;

    @Getter
    List<Income> incomes;

    @Getter
    @Setter
    String filterString = "";

    @PostConstruct
    public void init() {
        incomes = employeeService.findRecentIncomes();
    }

    public void handleIncomeEvent(@Observes IncomeEvent incomeEvent) {
        init();
    }

    public void sort(String attribute, boolean sortAsc) {
        incomes = employeeService.sort(attribute,sortAsc);
    }

    public void filter(ValueChangeEvent event) {
        Object value = event.getNewValue();
        UIComponent attribute = event.getComponent();

        String filterString = (String)value;
        String attributeString = attribute.getId();

        incomes = employeeService.filter(attributeString, filterString);
    }
}
