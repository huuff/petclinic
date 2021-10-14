package xyz.haff.petclinic.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
public class Pet extends AbstractBaseEntity {

    @Column(name = "name")
    @NotNull
    private String name;

    @Column(name = "birth_date")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate birthDate;

    @Column(name = "type")
    @NotNull
    @Enumerated(EnumType.STRING)
    private PetType type;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "owner")
    private Owner owner;
}
