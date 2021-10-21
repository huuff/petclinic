package xyz.haff.petclinic.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@SuperBuilder
public class Visit extends AbstractBaseEntity {

    @ManyToOne
    @ToString.Include
    @JoinColumn(name = "pet_id")
    private Pet pet;

    @ManyToOne
    @ToString.Include
    @JoinColumn(name = "vet_id")
    private Vet vet;

    @Column(name = "date")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate date;

    @Column(name = "reason")
    @ToString.Include
    private String reason;

    @Column(name = "vet_comment")
    private String vetComment;
}
