package xyz.haff.petclinic.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@Document
public class Vet extends Person {

    @NotNull
    private Specialty specialty;

    public Vet(String firstName, String lastName, Specialty specialty) {
        super(firstName, lastName);
        this.specialty = specialty;
    }
}
