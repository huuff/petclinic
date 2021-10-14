package xyz.haff.petclinic.models.forms;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import xyz.haff.petclinic.models.PetType;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PetForm {

    @NotNull(groups = CreationConstraintGroup.class)
    @NotEmpty(groups = CreationConstraintGroup.class)
    private String name;

    @NotNull(groups = CreationConstraintGroup.class)
    @Past
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    @NotNull(groups = CreationConstraintGroup.class)
    private PetType type;
}
