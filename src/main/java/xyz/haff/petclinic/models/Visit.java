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
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Table(name = "visits")
public class Visit extends AbstractBaseEntity {

    @JoinColumn(name = "pet_id")
    @ManyToOne
    private Pet pet;

    @JoinColumn(name = "vet_id")
    @ManyToOne
    private Vet vet;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "date")
    private LocalDate date;

    @Column(name = "reason")
    @Lob
    private String reason;
}
