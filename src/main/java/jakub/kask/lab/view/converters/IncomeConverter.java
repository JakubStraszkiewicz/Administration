package jakub.kask.lab.view.converters;

import jakub.kask.lab.business.boundary.EmployeeService;
import jakub.kask.lab.business.entities.Income;

import javax.enterprise.context.Dependent;
import javax.faces.convert.FacesConverter;

@FacesConverter(forClass = Income.class, managed = true)
@Dependent
public class IncomeConverter extends AbstractEntityConverter<Income> {

    public IncomeConverter() {
        super(Income::getId, EmployeeService::findIncome);
    }
}
