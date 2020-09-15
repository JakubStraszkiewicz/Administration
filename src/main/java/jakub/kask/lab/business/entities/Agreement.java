package jakub.kask.lab.business.entities;


import jakub.kask.lab.business.AgreementType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@Getter
@Setter
@NamedQueries({
        @NamedQuery(name = Agreement.Queries.FIND_ALL, query = "select a from Agreement a"),
        @NamedQuery(name = Agreement.Queries.FIND_ALL_UNASSIGNED, query =
                "SELECT a FROM Agreement a WHERE a NOT IN (SELECT DISTINCT a2 FROM Employee e join e.agreements a2)"),
        @NamedQuery(name = Agreement.Queries.FIND_BY_TYPE, query = "select a from Agreement a where a.agreementType = :agreementType")
})
public class Agreement {

    public static class Queries {
        public static final String FIND_ALL = "AGREEMENT_FIND_ALL";
        public static final String FIND_ALL_UNASSIGNED = "AGREEMENT_FIND_ALL_UNASSIGNED";
        public static final String FIND_BY_TYPE = "AGREEMENT_FIND_BY_TYPE";
    }

    @Id
    @GeneratedValue
    private Integer id;

    private AgreementType agreementType;

    @NotNull(message = "To pole jest wymagane")
    private Date endDate;

    @NotNull(message = "To pole jest wymagane")
    private Date startDate;


    public Agreement(AgreementType agreementType, Date startDate, Date endDate) {
        this.agreementType = agreementType;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
