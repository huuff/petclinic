package xyz.haff.petclinic.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pets")
public class Pet extends AbstractBaseEntity {

    @Column(name = "name")
    @NotBlank
    @NotNull
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    @NotNull
    private PetType type;

    @Column(name = "birth_date")
    @NotNull
    @Past
    private LocalDate birthDate;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "owner_id")
    private Owner owner;
}
