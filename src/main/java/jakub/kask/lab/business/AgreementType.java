package jakub.kask.lab.business;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

@NoArgsConstructor
@Getter
public enum AgreementType implements Serializable {
    contractOfMandate("Umowa zlecenie"), contractOfEmployment("Umowa o prace"), ContractOfWork("Umowa o dzieło");

    private String polishName;

    AgreementType(String polishName){
        this.polishName = polishName;
    }

    @Override
    public String toString() {
        return this.polishName;
    }

    public Collection<AgreementType> toList(){
        Collection<AgreementType> agreementNameList = new ArrayList<>();
        Collections.addAll(agreementNameList, AgreementType.values());
        return agreementNameList;
    }
}