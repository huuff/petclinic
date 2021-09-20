package xyz.haff.petclinic.mappers;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import xyz.haff.petclinic.forms.PetForm;
import xyz.haff.petclinic.models.Pet;

@Mapper
public interface PetToPetFormMapper extends Converter<Pet, PetForm> {

    @Override
    PetForm convert(Pet pet);
}
