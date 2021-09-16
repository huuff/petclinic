package xyz.haff.petclinic.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "owners")
public class Owner extends Person {
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner")
    @NotNull
    private Set<Pet> pets = new HashSet<>();

    public Owner(String firstName, String lastName, Set<Pet> pets) {
        super(firstName, lastName);
        this.pets = pets;
    }
}
