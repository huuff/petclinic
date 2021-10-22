package xyz.haff.petclinic.models.forms;

import lombok.*;
import xyz.haff.petclinic.models.Pet;
import xyz.haff.petclinic.models.Vet;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VisitForm {
    private String reason;
    private Pet pet;
    private Vet vet;
    private LocalDateTime dateTime;
    private String comment;
}
