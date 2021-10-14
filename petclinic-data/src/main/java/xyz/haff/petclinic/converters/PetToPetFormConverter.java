package xyz.haff.petclinic.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import xyz.haff.petclinic.models.Pet;
import xyz.haff.petclinic.models.forms.PetForm;

// TODO: TEST

@Component
public class PetToPetFormConverter implements Converter<Pet, PetForm> {
    @Override
    public PetForm convert(Pet pet) {
        return PetForm.builder()
                .name(pet.getName())
                .birthDate(pet.getBirthDate())
                .type(pet.getType())
                .build();
    }
}
