package xyz.haff.petclinic.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

// TODO: Set `firstName` and `lastName` as unique

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document
public abstract class Person extends AbstractBaseEntity {

    @NotBlank
    @NotNull
    private String firstName;

    @NotBlank
    @NotNull
    private String lastName;
}
