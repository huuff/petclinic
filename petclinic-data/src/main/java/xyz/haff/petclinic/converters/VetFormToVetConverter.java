package xyz.haff.petclinic.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import xyz.haff.petclinic.models.*;
import xyz.haff.petclinic.models.forms.PersonForm;
import xyz.haff.petclinic.models.forms.VetForm;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class VetFormToVetConverter implements Converter<VetForm, Vet> {
    private final Converter<PersonForm, PersonalData> personFormToPersonalData;

    @Override
    public Vet convert(VetForm vetForm) {
        var personalData = personFormToPersonalData.convert(vetForm);
        personalData.getUser().setRole(Role.VET);

        return Vet.builder()
                .specialty(vetForm.getSpecialty())
                .personalData(personalData)
                .build();
    }
}
