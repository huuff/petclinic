package xyz.haff.petclinic.models.forms;

import lombok.*;
import lombok.experimental.SuperBuilder;
import xyz.haff.petclinic.models.Specialty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;


@NoArgsConstructor
@SuperBuilder
public class VetForm extends PersonForm {
    @Getter
    @Setter
    @NotNull(groups= PersonForm.CreationConstraintGroup.class)
    private Specialty specialty;
}
