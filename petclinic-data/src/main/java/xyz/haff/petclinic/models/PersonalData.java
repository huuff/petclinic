package xyz.haff.petclinic.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class PersonalData extends AbstractBaseEntity {
    @NonNull private String firstName;
    @NonNull private String lastName;

    @NonNull
    @OneToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST })
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public String fullName() {
        return String.format("%s %s", firstName, lastName);
    }
}
