package xyz.haff.petclinic.models;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

// TODO: Maybe add address here too

@Entity
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class PersonalData extends AbstractBaseEntity {
    private String firstName;
    private String lastName;

    @OneToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST })
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @Builder.Default private User user = User.builder().build();

    public String fullName() {
        return String.format("%s %s", firstName, lastName);
    }
}
