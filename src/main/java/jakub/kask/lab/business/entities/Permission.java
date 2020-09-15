package jakub.kask.lab.business.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Entity
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@Getter
@Setter
@NamedQueries({
        @NamedQuery(name = Permission.Queries.FIND_ONE, query = "select p from Permission p where p.operation = :operation and p.role = :role")
})
public class Permission implements Serializable {

    public static class Queries {
        public static final String FIND_ONE = "PERMISSION_FIND_ONE";
    }

    @Id
    @GeneratedValue
    private Integer id;

    @NotBlank
    private String role;

    @NotBlank
    private String operation;

    @NotBlank
    private String permission;

    public Permission(String role, String operation, String permission) {
        this.role= role;
        this.operation = operation;
        this.permission = permission;
    }
}
