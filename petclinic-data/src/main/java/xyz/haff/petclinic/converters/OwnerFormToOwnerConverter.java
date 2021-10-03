package xyz.haff.petclinic.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import xyz.haff.petclinic.models.Owner;
import xyz.haff.petclinic.models.PersonalData;
import xyz.haff.petclinic.models.Role;
import xyz.haff.petclinic.models.User;
import xyz.haff.petclinic.models.forms.OwnerForm;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class OwnerFormToOwnerConverter implements Converter<OwnerForm, Owner> {
    private final Function<String, String> passwordEncodingFunction;

    @Override
    public Owner convert(OwnerForm ownerForm) {
        return Owner.builder()
                .personalData(PersonalData.builder()
                        .firstName(ownerForm.getFirstName())
                        .lastName(ownerForm.getLastName())
                        .user(User.builder()
                                .username(ownerForm.getUsername())
                                .password(passwordEncodingFunction.apply(ownerForm.getPassword()))
                                .role(Role.OWNER)
                                .build()
                        ).build()
                ).build();
    }
}
