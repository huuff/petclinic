package xyz.haff.petclinic.models.forms;

// At this point I'm beginning to be surprised at how good spring plays with inheritance
// Or how bad it plays with composition for that matter
// I would use delegation if it weren't so verbose

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import xyz.haff.petclinic.validation.PasswordEqualsRepeat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;
import java.util.Objects;

// MAYBE: Use kotlin with delegation so this is a component of owner and vet form instead of an abstract class?

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@PasswordEqualsRepeat(groups = { Default.class, CreationConstraintGroup.class })
public abstract class PersonForm {
    @NotNull(groups=CreationConstraintGroup.class)
    @NotEmpty(groups=CreationConstraintGroup.class)
    private String firstName;
    @NotNull(groups=CreationConstraintGroup.class)
    @NotEmpty(groups=CreationConstraintGroup.class)
    private String lastName;
    @NotNull(groups=CreationConstraintGroup.class)
    @NotEmpty(groups=CreationConstraintGroup.class)
    private String username;

    @NotNull(groups=CreationConstraintGroup.class)
    @NotEmpty(groups=CreationConstraintGroup.class)
    private String password;
    @NotNull(groups=CreationConstraintGroup.class)
    @NotEmpty(groups=CreationConstraintGroup.class)
    private String repeatPassword;
}
