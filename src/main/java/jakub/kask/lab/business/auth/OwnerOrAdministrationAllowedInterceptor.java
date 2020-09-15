package jakub.kask.lab.business.auth;

import jakub.kask.lab.business.entities.Income;
import jakub.kask.lab.business.entities.User;

import javax.annotation.Priority;
import javax.annotation.Resource;
import javax.ejb.EJBAccessException;
import javax.ejb.SessionContext;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;

@Interceptor
@OwnerOrAdministrationAllowed
@Priority(1000)
public class OwnerOrAdministrationAllowedInterceptor implements Serializable {

    @Resource
    SessionContext sessionCtx;

    @PersistenceContext
    EntityManager em;

    @AroundInvoke
    public Object invoke(InvocationContext context) throws Exception {
        Object parameter = context.getParameters()[0];

        if (parameter instanceof Income) {
            Income income = (Income) parameter;

            if (isNewIncome(income) || isAdministration() || isOwner(income)) {
                return context.proceed();
            }
        }

        throw new EJBAccessException("Client not authorized for this invocation");
    }

    private boolean isOwner(Income income) {
        String login = sessionCtx.getCallerPrincipal().getName();
        Income originalIncome = em.find(Income.class, income.getId());
        em.detach(originalIncome);
        return originalIncome.getOwner().getLogin().equals(login);
    }

    private boolean isAdministration() {
        return sessionCtx.isCallerInRole(User.Roles.ADMINISTRATION);
    }

    private boolean isNewIncome(Income income) {
        return income.getId() == null;
    }
}
