package xyz.haff.petclinic.models;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

// TODO: See if I can use a parent `Person` class? An object with delegation?

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Owner extends AbstractBaseEntity {

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    private Set<Pet> pets = new HashSet<>();
}
