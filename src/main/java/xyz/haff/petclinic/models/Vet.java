package xyz.haff.petclinic.models;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "vets")
public class Vet extends Person {
    @Enumerated(EnumType.STRING)
    @Column(name = "specialty")
    private Specialty specialty;

    public Vet(String firstName, String lastName, Specialty specialty) {
        super(firstName, lastName);
        this.specialty = specialty;
    }
}
