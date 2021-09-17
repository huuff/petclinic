package xyz.haff.petclinic.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import xyz.haff.petclinic.forms.PetForm;
import xyz.haff.petclinic.models.Pet;

@Component
public class PetToPetFormConverter implements Converter<Pet, PetForm> {

    @Override
    public PetForm convert(Pet pet) {
        var petForm = new PetForm();
        petForm.setName(pet.getName());
        petForm.setBirthDate(pet.getBirthDate());
        petForm.setType(pet.getType());

        return petForm;
    }
}
