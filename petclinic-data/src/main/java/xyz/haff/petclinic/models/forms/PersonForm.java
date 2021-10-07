package xyz.haff.petclinic.models.forms;

// At this point I'm beginning to be surprised at how good spring plays with inheritance
// Or how bad it plays with composition for that matter
// I would use delegation if it weren't so verbose

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public abstract class PersonForm {
    public interface CreationConstraintGroup {}

    @NotNull(groups= PersonForm.CreationConstraintGroup.class)
    @NotEmpty(groups= PersonForm.CreationConstraintGroup.class)
    private String firstName;
    @NotNull(groups= PersonForm.CreationConstraintGroup.class)
    @NotEmpty(groups= PersonForm.CreationConstraintGroup.class)
    private String lastName;
    @NotNull(groups= PersonForm.CreationConstraintGroup.class)
    @NotEmpty(groups= PersonForm.CreationConstraintGroup.class)
    private String username;

    @NotNull(groups= PersonForm.CreationConstraintGroup.class)
    @NotEmpty(groups= PersonForm.CreationConstraintGroup.class)
    private String password;
    @NotNull(groups= PersonForm.CreationConstraintGroup.class)
    @NotEmpty(groups= PersonForm.CreationConstraintGroup.class)
    private String repeatPassword;

    // TODO: I might be able to do some custom validation for this
    public boolean passwordEqualsRepeatPassword() {
        return Objects.equals(password, repeatPassword);
    }
}
