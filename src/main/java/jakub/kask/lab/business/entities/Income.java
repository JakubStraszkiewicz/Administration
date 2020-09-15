package jakub.kask.lab.business.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.REFRESH;

@Entity
@EqualsAndHashCode(of = "id", callSuper = false)
@NoArgsConstructor
@Getter
@Setter
@NamedQueries({
        @NamedQuery(name = Income.Queries.FIND_ALL, query = "select i from Income i"),
        @NamedQuery(name = Income.Queries.FIND_MOST_RECENT, query = "SELECT i FROM Income i ORDER BY i.modificationDate DESC"),

})
@NamedEntityGraphs({
        @NamedEntityGraph(
                name = Income.Graphs.WITH_EMPLOYEE,
                attributeNodes = {@NamedAttributeNode("employee"), @NamedAttributeNode("owner")})
})
public class Income extends Audit implements Serializable {

    public static class Queries {
        public static final String FIND_ALL = "INCOME_FIND_ALL";
        public static final String FIND_MOST_RECENT = "INCOME_FIND_MOST_RECENT";
    }

    public static class Graphs {
        public static final String WITH_EMPLOYEE="Invoice{employee}";
        public static final String WITH_USER="Invoice{user}";
    }

    @Id
    @GeneratedValue
    private Integer id;

    @NotNull(message = "Pole nie może zostać puste")
    @Min(value = 0, message = "Wartość musi być dodatnia")
    private int salary;

    @NotNull(message = "Pole nie może zostać puste")
    @Min(value = 0, message = "Wartość musi być dodatnia")
    private int allowance;

    @NotNull(message = "Pole nie może zostać puste")
    private Date settlementDate;

    @ManyToOne(cascade = {MERGE, REFRESH})
    @NotNull(message = "Pole nie może zostać puste")
    private Employee employee;

    @Getter
    @Setter
    @ManyToOne
    @NotNull
    @JsonIgnore
    private User owner;

    public Income(int salary, int allowance, Date settlementDate, Employee employee, User owner) {
        this.salary = salary;
        this.allowance = allowance;
        this.settlementDate = settlementDate;
        this.employee = employee;
        this.owner = owner;
    }
}
