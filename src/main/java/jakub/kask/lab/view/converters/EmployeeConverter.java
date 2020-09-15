package jakub.kask.lab.view.converters;

import jakub.kask.lab.business.boundary.EmployeeService;
import jakub.kask.lab.business.entities.Employee;

import javax.enterprise.context.Dependent;
import javax.faces.convert.FacesConverter;

@FacesConverter(forClass = Employee.class, managed = true)
@Dependent
public class EmployeeConverter extends AbstractEntityConverter<Employee> {

    public EmployeeConverter() {
        super(Employee::getId, EmployeeService::findEmployee);
    }
}
