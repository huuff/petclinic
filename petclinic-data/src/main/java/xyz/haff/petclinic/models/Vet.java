package xyz.haff.petclinic.models;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Getter
@Setter
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@NoArgsConstructor
@SuperBuilder
public class Vet extends Person {
    @Enumerated(EnumType.STRING)
    @Column(name = "specialty")
    @ToString.Include
    @NotNull
    private Specialty specialty;

    @OneToMany(mappedBy = "vet")
    private List<Visit> visits;
}
