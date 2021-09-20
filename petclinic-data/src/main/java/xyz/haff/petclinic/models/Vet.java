package xyz.haff.petclinic.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "vets")
public class Vet extends Person {
    @Enumerated(EnumType.STRING)
    @Column(name = "specialty")
    @NotNull
    private Specialty specialty;

    public Vet(String firstName, String lastName, Specialty specialty) {
        super(firstName, lastName);
        this.specialty = specialty;
    }
}
