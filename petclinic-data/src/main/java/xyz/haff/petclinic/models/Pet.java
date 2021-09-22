package xyz.haff.petclinic.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pet extends AbstractBaseEntity {
    @NotBlank
    @NotNull
    private String name;

    @NotNull
    private PetType type;

    @NotNull
    @Past
    private LocalDate birthDate;

    private List<Visit> visits = new ArrayList<>();

    public Pet(String name, PetType type, LocalDate birthDate) {
        this.name = name;
        this.type = type;
        this.birthDate = birthDate;
    }
}
