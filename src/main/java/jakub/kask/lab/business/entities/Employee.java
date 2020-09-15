package jakub.kask.lab.business.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.REFRESH;

@Entity
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@Getter
@Setter
@NamedQueries({
        @NamedQuery(name = Employee.Queries.FIND_ALL, query = "select e from Employee e")
})
@NamedEntityGraphs({
        @NamedEntityGraph(
                name = Employee.Graphs.WITH_AGREEMENTS,
                attributeNodes = {@NamedAttributeNode("agreements")})
})
public class Employee implements  Serializable {

    public static class Queries {
        public static final String FIND_ALL = "EMPLOYEE_FIND_ALL";
    }

    public static class Graphs {
        public static final String WITH_AGREEMENTS="Invoice{agreements}";
    }

    @Id
    @GeneratedValue
    private Integer id;

    @NotBlank(message = "Pole wymagane")
    @Pattern(regexp = "[0-9]{11}", message = "Pesel to ciąg 11 cyfr")
    private String pesel;

    @NotBlank(message = "Pole wymagane")
    @Pattern(regexp = "([A-Za-z]|[^\\x00-\\x7F])+", message = "Imię może składać się tylko z dużych i małych liter")
    private String name;

    @NotBlank(message = "Pole wymagane")
    @Pattern(regexp = "([A-Za-z]|[^\\x00-\\x7F])+", message = "Nazwisko może składać się tylko z dużych i małych liter")
    private String surname;

    @Size(min = 1, message = "Pole wymagane")
    @OneToMany(cascade = {MERGE, REFRESH})
    private List<Agreement> agreements = new ArrayList<>();

    public Employee(String pesel, String name, String surname, @Size(min = 1) List<Agreement> agreements) {
        this.pesel = pesel;
        this.name = name;
        this.surname = surname;
        this.agreements = agreements;
    }
}
