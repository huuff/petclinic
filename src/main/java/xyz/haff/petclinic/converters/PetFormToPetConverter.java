package xyz.haff.petclinic.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import xyz.haff.petclinic.forms.PetForm;
import xyz.haff.petclinic.models.Pet;

@Component
public class PetFormToPetConverter implements Converter<PetForm, Pet> {

    @Override
    public Pet convert(PetForm petForm) {
        var pet = new Pet();
        pet.setBirthDate(petForm.getBirthDate());
        pet.setName(petForm.getName());
        pet.setType(petForm.getType());

        return pet;
    }
}
