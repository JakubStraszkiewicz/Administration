package jakub.kask.lab.view;

import jakub.kask.lab.business.entities.Income;

import java.io.Serializable;
import java.util.List;

public interface IncomeSource extends Serializable {
    List<Income> getIncomes();
}
