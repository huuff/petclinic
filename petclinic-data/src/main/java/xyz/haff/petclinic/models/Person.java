package xyz.haff.petclinic.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.MappedSuperclass;

@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
@SuperBuilder
public abstract class Person extends AbstractBaseEntity {
    @NonNull private String firstName;
    @NonNull private String lastName;

    public String fullName() {
        return String.format("%s %s", firstName, lastName);
    }
}
