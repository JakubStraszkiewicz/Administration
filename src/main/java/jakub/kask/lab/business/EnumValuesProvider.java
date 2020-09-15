package jakub.kask.lab.business;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class EnumValuesProvider implements Serializable {

    public AgreementType[] getAgreementTypes(){
        return AgreementType.values();
    }

}