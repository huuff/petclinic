package xyz.haff.petclinic.models.forms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OwnerForm {
    @NotNull(groups=NewOwnerConstraintGroup.class)
    @NotEmpty(groups=NewOwnerConstraintGroup.class)
    private String firstName;
    @NotNull(groups=NewOwnerConstraintGroup.class)
    @NotEmpty(groups=NewOwnerConstraintGroup.class)
    private String lastName;
    @NotNull(groups=NewOwnerConstraintGroup.class)
    @NotEmpty(groups=NewOwnerConstraintGroup.class)
    private String username;

    @NotNull(groups=NewOwnerConstraintGroup.class)
    @NotEmpty(groups=NewOwnerConstraintGroup.class)
    private String password;
    @NotNull(groups=NewOwnerConstraintGroup.class)
    @NotEmpty(groups=NewOwnerConstraintGroup.class)
    private String repeatPassword;

    public boolean passwordEqualsRepeatPassword() {
        return Objects.equals(password, repeatPassword);
    }

    public interface NewOwnerConstraintGroup {}
}
