package jakub.kask.lab.view;

import jakub.kask.lab.business.entities.User;
import org.omnifaces.util.Faces;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Serializable;

@Named
@RequestScoped
public class Main implements Serializable {

    @Inject
    AuthContext authContext;

    public void logout() throws ServletException, IOException {
        HttpServletRequest request = JSFUtils.getRequest();
        request.logout();
        request.getSession().invalidate();
        Faces.redirect("/lab-1.0-SNAPSHOT/");
    }

    public boolean isUserLogged() {
        return authContext.isAuthenticated();
    }

    public boolean isUserAdministration() {
        return authContext.isUserInRole(User.Roles.ADMINISTRATION);
    }


}
