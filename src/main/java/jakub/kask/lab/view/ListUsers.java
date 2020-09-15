package jakub.kask.lab.view;

import jakub.kask.lab.business.boundary.UserService;
import jakub.kask.lab.business.entities.User;
import lombok.Getter;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class ListUsers implements Serializable {

    @EJB
    UserService userService;

    @Getter
    List<User> users;

    @PostConstruct
    public void init() {
        users = userService.findAllUsers();
    }
}
