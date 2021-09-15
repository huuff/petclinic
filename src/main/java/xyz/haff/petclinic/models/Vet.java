package xyz.haff.petclinic.models;

// TODO: See if I can use a parent `Person` class? An object with delegation?

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "vets")
public class Vet extends AbstractBaseEntity {
    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(name = "specialty")
    private Specialty specialty;
}
