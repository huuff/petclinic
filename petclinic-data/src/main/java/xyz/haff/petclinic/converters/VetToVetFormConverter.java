package xyz.haff.petclinic.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import xyz.haff.petclinic.models.Vet;
import xyz.haff.petclinic.models.forms.VetForm;

// MAYBE: This would be less duplicated if I used composition over inheritance, although Java and Spring make it more
// cumbersome. It isn't too much duplication in any case.
@Component
public class VetToVetFormConverter implements Converter<Vet, VetForm> {

    @Override
    public VetForm convert(Vet vet) {
        return VetForm.builder()
                .firstName(vet.getPersonalData().getFirstName())
                .lastName(vet.getPersonalData().getLastName())
                .username(vet.getPersonalData().getUser().getUsername())
                .specialty(vet.getSpecialty())
                .password("")
                .repeatPassword("")
                .build();
    }
}
