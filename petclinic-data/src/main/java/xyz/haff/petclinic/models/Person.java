package xyz.haff.petclinic.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;

@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
@SuperBuilder
public abstract class Person extends AbstractBaseEntity {
    @NonNull
    @OneToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST })
    @JoinColumn(name = "personal_data_id", referencedColumnName = "id")
    private PersonalData personalData;
}
