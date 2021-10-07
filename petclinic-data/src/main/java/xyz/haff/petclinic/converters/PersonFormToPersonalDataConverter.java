package xyz.haff.petclinic.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import xyz.haff.petclinic.models.PersonalData;
import xyz.haff.petclinic.models.User;
import xyz.haff.petclinic.models.forms.PersonForm;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class PersonFormToPersonalDataConverter implements Converter<PersonForm, PersonalData> {
    private final Function<String, String> passwordEncodingFunction;

    @Override
    public PersonalData convert(PersonForm personForm) {
        return PersonalData.builder()
                .firstName(personForm.getFirstName())
                .lastName(personForm.getLastName())
                .user(User.builder()
                        .username(personForm.getUsername())
                        .password(passwordEncodingFunction.apply(personForm.getPassword()))
                .build())
                .build();
    }
}
