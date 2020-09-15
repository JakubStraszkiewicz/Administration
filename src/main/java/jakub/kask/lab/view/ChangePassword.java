package jakub.kask.lab.view;

import jakub.kask.lab.business.auth.CryptUtils;
import jakub.kask.lab.business.boundary.UserService;
import jakub.kask.lab.business.entities.User;
import lombok.Getter;
import lombok.Setter;
import org.omnifaces.util.Messages;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class ChangePassword implements Serializable {

    @Inject
    private UserService userService;

    @Getter
    @Setter
    private String oldPassword = "";

    @Getter
    @Setter
    private String newPassword = "";

    @Getter
    @Setter
    private String repeatedPassword = "";

    public String changePassword() {
        User currentUser = userService.findCurrentUser();

        if(currentUser.getPassword().equals(CryptUtils.sha256(oldPassword)))
        {
            if(newPassword.equals(repeatedPassword))
            {
                userService.changePassword(newPassword);
                return "list_agreements?faces-redirect=true";
            }
            Messages.addError("change-password-form", "Wpisane hasła nie są takie same");
            return null;
        }
        Messages.addError("change-password-form", "Aktualne hasło się nie zgadza");
        return null;
    }
}
