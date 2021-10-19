package xyz.haff.petclinic.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
public class Pet extends AbstractBaseEntity {

    @ToString.Include
    @Column(name = "name")
    @NotNull
    private String name;

    @ToString.Include
    @Column(name = "birth_date")
    @Past
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate birthDate;

    @ToString.Include
    @Column(name = "type")
    @NotNull
    @Enumerated(EnumType.STRING)
    private PetType type;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "owner_id")
    private Owner owner;
}
