package jakub.kask.lab.view;

import jakub.kask.lab.business.boundary.UserService;
import jakub.kask.lab.business.entities.User;
import lombok.Getter;
import lombok.Setter;
import org.omnifaces.util.Messages;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class EditUser implements Serializable {

    @Inject
    private UserService userService;

    @Getter
    @Setter
    private User user = new User();

    public List<String> findAvailableRoles() {
        return userService.findAvailableRoles();
    }

    public String saveUser() {

        if(userService.findCurrentUser().getId().equals(user.getId()) &&
           !user.getRoles().contains(User.Roles.ADMINISTRATION)) {
            Messages.addError("changeRole", "Nie możesz pozbyć się roli administratora");
            return null;
        }

        userService.saveUser(user);
        return "list_users?faces-redirect=true";
    }
}
