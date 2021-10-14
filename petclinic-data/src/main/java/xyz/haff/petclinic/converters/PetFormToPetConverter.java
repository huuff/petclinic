package xyz.haff.petclinic.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import xyz.haff.petclinic.models.Pet;
import xyz.haff.petclinic.models.forms.PetForm;

// TODO: Test

@Component
public class PetFormToPetConverter implements Converter<PetForm, Pet> {

    @Override
    public Pet convert(PetForm petForm) {
        return Pet.builder()
                .name(petForm.getName())
                .birthDate(petForm.getBirthDate())
                .type(petForm.getType())
                .build();
    }
}
