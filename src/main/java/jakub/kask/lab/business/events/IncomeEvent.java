package jakub.kask.lab.business.events;

import jakub.kask.lab.business.entities.Income;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor(staticName = "of")
@NoArgsConstructor(staticName = "on")
public class IncomeEvent {
    @Getter
    @Setter
    Income income;
}
