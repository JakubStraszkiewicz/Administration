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
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Serializable;

import static java.util.Arrays.asList;

@Named
@ViewScoped
public class LoginForm implements Serializable {

    @Inject
    private UserService userService;

    @Getter
    @Setter
    private String login;

    @Getter
    @Setter
    private String password;

    @Getter
    @Setter
    private String repeatedRegistryPassword;

    @Getter
    @Setter
    private User user = new User();

    public String login() throws IOException {
        try {
            HttpServletRequest request = JSFUtils.getRequest();
            request.login(login, password);
            return "/employees/list_employees.xhtml?faces-redirect=true";

        } catch (ServletException ex) {
            Messages.addError("login-form", "Login failed");
            return null;
        }
    }

    public String registry() throws IOException {

        if(user.getPassword().equals(repeatedRegistryPassword))
        {
            user.setPassword(CryptUtils.sha256(repeatedRegistryPassword));
            user.setRoles(asList(User.Roles.USER));
            userService.registryUser(user);
            login = user.getLogin();
            password = repeatedRegistryPassword;
            return login();
        }
        Messages.addError("registry-form", "Wpisane hasła nie są takie same");
        return null;
    }

}
