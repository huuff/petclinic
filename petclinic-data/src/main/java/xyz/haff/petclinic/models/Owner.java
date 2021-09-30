package xyz.haff.petclinic.models;

import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;

@Entity
@NoArgsConstructor
@SuperBuilder
public class Owner extends Person {
}
