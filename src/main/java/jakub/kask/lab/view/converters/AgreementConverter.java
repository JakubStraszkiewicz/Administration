package jakub.kask.lab.view.converters;

import jakub.kask.lab.business.boundary.EmployeeService;
import jakub.kask.lab.business.entities.Agreement;

import javax.enterprise.context.Dependent;
import javax.faces.convert.FacesConverter;

@FacesConverter(forClass = Agreement.class, managed = true)
@Dependent
public class AgreementConverter extends AbstractEntityConverter<Agreement> {

    public AgreementConverter() {
        super(Agreement::getId, EmployeeService::findAgreement);
    }
}
