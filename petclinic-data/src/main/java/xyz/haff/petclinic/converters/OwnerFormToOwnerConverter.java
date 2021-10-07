package xyz.haff.petclinic.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import xyz.haff.petclinic.models.Owner;
import xyz.haff.petclinic.models.PersonalData;
import xyz.haff.petclinic.models.Role;
import xyz.haff.petclinic.models.User;
import xyz.haff.petclinic.models.forms.OwnerForm;
import xyz.haff.petclinic.models.forms.PersonForm;

import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class OwnerFormToOwnerConverter implements Converter<OwnerForm, Owner> {
    private final Converter<PersonForm, PersonalData> personFormToPersonalData;

    @Override
    public Owner convert(OwnerForm ownerForm) {
        var personalData = personFormToPersonalData.convert(ownerForm);
        personalData.getUser().setRole(Role.OWNER);

        return Owner.builder().personalData(personalData).build();
    }
}