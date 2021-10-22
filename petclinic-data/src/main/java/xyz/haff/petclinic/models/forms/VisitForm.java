package xyz.haff.petclinic.models.forms;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import xyz.haff.petclinic.models.Pet;
import xyz.haff.petclinic.models.Vet;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VisitForm {
    @NotNull
    @NotEmpty
    private String reason;
    @NotNull
    private Pet pet;
    private Vet vet;
    @NotNull
    @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm")
    private LocalDateTime dateTime;
    private String comment;
}
