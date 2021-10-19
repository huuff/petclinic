package xyz.haff.petclinic.models;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;

@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
@ToString(callSuper = true)
@SuperBuilder
public abstract class Person extends AbstractBaseEntity {
    @OneToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST })
    @JoinColumn(name = "personal_data_id", referencedColumnName = "id")
    @Builder.Default private PersonalData personalData = PersonalData.builder().build();
}
