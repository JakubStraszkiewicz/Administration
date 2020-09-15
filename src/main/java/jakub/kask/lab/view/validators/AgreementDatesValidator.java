package jakub.kask.lab.view.validators;

import org.omnifaces.validator.MultiFieldValidator;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@ManagedBean
@RequestScoped
public class AgreementDatesValidator implements MultiFieldValidator {

    @Override
    public boolean validateValues(FacesContext context, List<UIInput> list, List<Object> list1) {

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate, endDate;

        checkNullContaining(context,list,list1);

        try {
            startDate = format.parse(list1.get(0).toString());
        } catch (ParseException e) {
            return false;
        }

        try {
            endDate = format.parse(list1.get(1).toString());
        } catch (ParseException e) {
            return false;
        }

        if(startDate.after(endDate)) {

            FacesMessage msg=new FacesMessage("Data rozpoczęcia musi być wcześniej niż data zakończenia");
            context.addMessage(list.get(0).getClientId(context),msg);
            context.addMessage(list.get(1).getClientId(context),msg);

            return false;
        }

        return true;

    }

    private void checkNullContaining(FacesContext context, List<UIInput> controls, List<Object> objects) {
        FacesMessage msg=new FacesMessage("To pole jest wymagane");

        if(objects.get(0).toString().isEmpty()){
            context.addMessage(controls.get(0).getClientId(context),msg);
        }

        if(objects.get(1).toString().isEmpty()){
            context.addMessage(controls.get(1).getClientId(context),msg);
        }
    }
}
