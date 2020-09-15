package jakub.kask.lab.business.boundary;

import jakub.kask.lab.business.auth.Authorize;
import jakub.kask.lab.business.auth.CryptUtils;
import jakub.kask.lab.business.entities.User;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Stateless
@PermitAll
@Authorize
public class UserService implements Serializable {

    @PersistenceContext
    EntityManager em;

    @Resource
    SessionContext sessionCtx;

    public List<User> findAllUsers() {
        return em.createNamedQuery(User.Queries.FIND_ALL, User.class).getResultList();
    }

    public User findUser(int id) {
        return em.find(User.class, id);
    }

    public User findCurrentUser() {
        String login = sessionCtx.getCallerPrincipal().getName();
        return findUserByLogin(login);
    }

    @Transactional
    public User changePassword(String newPassword) {
        String login = sessionCtx.getCallerPrincipal().getName();
        User user = this.findUserByLogin(login);
        user.setPassword(CryptUtils.sha256(newPassword));

        user = em.merge(user);
        return user;
    }

    private User findUserByLogin(String login) {
        TypedQuery<User> query = em.createNamedQuery(User.Queries.FIND_BY_LOGIN, User.class);
        query.setParameter("login", login);
        return query.getSingleResult();
    }

    @Transactional
    public User saveUser(User user) {
        user = em.merge(user);
        return user;
    }

    @Transactional
    public User registryUser(User user) {
        em.persist(user);
        return user;
    }

    public List<String> findAvailableRoles() {
        List<String> roles = new ArrayList<>();
        roles.add(User.Roles.ADMINISTRATION);
        roles.add(User.Roles.USER);
        return roles;
    }
}
