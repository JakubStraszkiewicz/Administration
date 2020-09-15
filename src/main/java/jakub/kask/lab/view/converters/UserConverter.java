package jakub.kask.lab.view.converters;

import jakub.kask.lab.business.boundary.UserService;
import jakub.kask.lab.business.entities.User;

import javax.enterprise.context.Dependent;
import javax.faces.convert.FacesConverter;

@FacesConverter(forClass = User.class, managed = true)
@Dependent
public class UserConverter extends AbstractUserConverter<User> {

    public UserConverter() {
        super(User::getId, UserService::findUser);
    }
}
