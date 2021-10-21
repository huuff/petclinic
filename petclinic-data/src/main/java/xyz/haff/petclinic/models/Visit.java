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
import java.time.LocalDateTime;

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

    @Column(name = "date_time")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime dateTime;

    @Column(name = "reason")
    @ToString.Include
    private String reason;

    @Column(name = "vet_comment")
    private String vetComment;
}
