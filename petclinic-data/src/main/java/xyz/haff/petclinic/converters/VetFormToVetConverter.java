package xyz.haff.petclinic.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import xyz.haff.petclinic.models.*;
import xyz.haff.petclinic.models.forms.VetForm;

import java.util.function.Function;

// TODO: More highly duplicated code! DRY!!

@Component
@RequiredArgsConstructor
public class VetFormToVetConverter implements Converter<VetForm, Vet> {
    private final Function<String, String> passwordEncodingFunction;

    @Override
    public Vet convert(VetForm vetForm) {
        return Vet.builder()
                .specialty(vetForm.getSpecialty())
                .personalData(PersonalData.builder()
                        .firstName(vetForm.getFirstName())
                        .lastName(vetForm.getLastName())
                        .user(User.builder()
                                .username(vetForm.getUsername())
                                .password(passwordEncodingFunction.apply(vetForm.getPassword()))
                                .role(Role.OWNER)
                                .build()
                        ).build()
                ).build();
    }
}
