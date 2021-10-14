package xyz.haff.petclinic.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
public class Pet extends AbstractBaseEntity {

    @Column(name = "birth_date")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate birthDate;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private PetType type;

    @ManyToOne
    @JoinColumn(name = "owner")
    private Owner owner;
}
