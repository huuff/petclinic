package xyz.haff.petclinic.models.forms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.haff.petclinic.models.Specialty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;

// TODO: This is more or less the same object as the owner form, try to merge them somehow

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VetForm {
    @NotNull(groups= VetForm.NewVetConstraintGroup.class)
    @NotEmpty(groups= VetForm.NewVetConstraintGroup.class)
    private String firstName;
    @NotNull(groups= VetForm.NewVetConstraintGroup.class)
    @NotEmpty(groups= VetForm.NewVetConstraintGroup.class)
    private String lastName;
    @NotNull(groups= VetForm.NewVetConstraintGroup.class)
    @NotEmpty(groups= VetForm.NewVetConstraintGroup.class)
    private String username;

    @NotNull(groups= VetForm.NewVetConstraintGroup.class)
    @NotEmpty(groups= VetForm.NewVetConstraintGroup.class)
    private String password;
    @NotNull(groups= VetForm.NewVetConstraintGroup.class)
    @NotEmpty(groups= VetForm.NewVetConstraintGroup.class)
    private String repeatPassword;

    @NotNull(groups= VetForm.NewVetConstraintGroup.class)
    private Specialty specialty;

    // TODO: I might be able to do some custom validation for this
    public boolean passwordEqualsRepeatPassword() {
        return Objects.equals(password, repeatPassword);
    }

    public interface NewVetConstraintGroup {}
}
