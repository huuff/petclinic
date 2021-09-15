package xyz.haff.petclinic.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pets")
public class Pet extends AbstractBaseEntity {

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private PetType type;

    @Column(name = "birth_date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate birthDate;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;
}
