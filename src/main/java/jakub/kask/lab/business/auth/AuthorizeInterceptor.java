package jakub.kask.lab.business.auth;

import jakub.kask.lab.business.boundary.UserService;
import jakub.kask.lab.business.entities.Income;
import jakub.kask.lab.business.entities.Permission;
import jakub.kask.lab.business.entities.User;
import jakub.kask.lab.view.AuthContext;

import javax.annotation.Priority;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBAccessException;
import javax.ejb.SessionContext;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.util.List;

@Interceptor
@Authorize
@Priority(1000)
public class AuthorizeInterceptor implements Serializable {

    @Resource
    SessionContext sessionCtx;

    @PersistenceContext
    EntityManager em;

    @EJB
    UserService userService;

    @Inject
    AuthContext authContext;

    @AroundInvoke
    public Object invoke(InvocationContext context) throws Exception {

        if(!authContext.isAuthenticated())
        {
            String operation = context.getMethod().getName();
            Permission permission = getPermission(operation, "ANONYMOUS");
            if(checkPermission(permission, context))
            {
                return context.proceed();
            }
            else
            {
                throw new EJBAccessException("Client not authorized for this invocation");
            }
        }

        User user = findCurrentUser();
        String operation = context.getMethod().getName();

        if(user.getRoles().contains(User.Roles.ADMINISTRATION)) {
            return authorize(user,operation,User.Roles.ADMINISTRATION,context);
        }
        if(user.getRoles().contains(User.Roles.USER)) {
            return authorize(user,operation,User.Roles.USER,context);
        }


        throw new EJBAccessException("Client not authorized for this invocation");
    }

    private Object authorize(User user, String operation, String role, InvocationContext context) throws Exception {
            Permission permission = getPermission(operation, role);
            if(checkPermission(permission, context))
            {
                return context.proceed();
            }
            else
            {
                throw new EJBAccessException("Client not authorized for this invocation");
            }
    }

    private boolean checkPermission(Permission permission, InvocationContext context)
    {
        switch(permission.getPermission()){
            case "GRANTED":
                return true;
            case "DENIED":
                return false;
            case "IF_OWNER":
                Object parameter = context.getParameters()[0];

                if (parameter instanceof Income) {
                    Income income = (Income) parameter;

                    if (isNewIncome(income) || isOwner(income)) {
                        return true;
                    } else {
                        return false;
                    }
                }
            default:
                return false;
        }
    }

    private Permission getPermission(String operation,String role) {
        TypedQuery<Permission> query = em.createNamedQuery(Permission.Queries.FIND_ONE, Permission.class);
        query.setParameter("operation", operation);
        query.setParameter("role", role);
        return query.getSingleResult();
    }

    private boolean isOwner(Income income) {
        String login = sessionCtx.getCallerPrincipal().getName();
        Income originalIncome = em.find(Income.class, income.getId());
        em.detach(originalIncome);
        return originalIncome.getOwner().getLogin().equals(login);
    }

    private boolean isNewIncome(Income income) {
        return income.getId() == null;
    }

    private User findCurrentUser() {
        String login = sessionCtx.getCallerPrincipal().getName();
        TypedQuery<User> query = em.createNamedQuery(User.Queries.FIND_BY_LOGIN, User.class);
        query.setParameter("login", login);
        return query.getSingleResult();
    }
}
