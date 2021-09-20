package xyz.haff.petclinic.mappers;

import org.mapstruct.Mapper;
import org.springframework.core.convert.converter.Converter;
import xyz.haff.petclinic.forms.PetForm;
import xyz.haff.petclinic.models.Pet;

@Mapper
public interface PetFormToPetMapper extends Converter<PetForm, Pet> {

    @Override
    Pet convert(PetForm petForm);
}
