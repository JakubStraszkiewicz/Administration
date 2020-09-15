package jakub.kask.lab.view;

import jakub.kask.lab.business.events.IncomeEvent;
import org.omnifaces.cdi.Push;
import org.omnifaces.cdi.PushContext;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

@ApplicationScoped
public class IncomeEventPropagator {

    @Inject
    @Push
    PushContext incomeUpdates;

    public void handleIncomeEvent(@Observes IncomeEvent incomeEvent) {
        incomeUpdates.send("update");
    }
}
