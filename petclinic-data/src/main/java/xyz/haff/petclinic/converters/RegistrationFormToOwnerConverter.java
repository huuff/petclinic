package xyz.haff.petclinic.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import xyz.haff.petclinic.models.Owner;
import xyz.haff.petclinic.models.PersonalData;
import xyz.haff.petclinic.models.Role;
import xyz.haff.petclinic.models.User;
import xyz.haff.petclinic.models.forms.RegistrationForm;

import javax.validation.constraints.NotNull;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class RegistrationFormToOwnerConverter implements Converter<RegistrationForm, Owner> {
    private final Function<String, String> passwordEncodingFunction;

    @Override
    public Owner convert(RegistrationForm registrationForm) {
        return Owner.builder()
                .personalData(PersonalData.builder()
                        .firstName(registrationForm.getFirstName())
                        .lastName(registrationForm.getLastName())
                        .user(User.builder()
                                .username(registrationForm.getUsername())
                                .password(passwordEncodingFunction.apply(registrationForm.getPassword()))
                                .role(Role.OWNER)
                                .build()
                        ).build()
                ).build();
    }
}
