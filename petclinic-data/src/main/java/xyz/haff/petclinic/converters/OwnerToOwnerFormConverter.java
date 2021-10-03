package xyz.haff.petclinic.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import xyz.haff.petclinic.models.Owner;
import xyz.haff.petclinic.models.forms.OwnerForm;

// TODO: Test
@Component
public class OwnerToOwnerFormConverter implements Converter<Owner, OwnerForm> {

    @Override
    public OwnerForm convert(Owner owner) {
        return new OwnerForm(
                owner.getPersonalData().getFirstName(),
                owner.getPersonalData().getLastName(),
                owner.getPersonalData().getUser().getUsername(),
                "",
                ""
        );
    }
}
