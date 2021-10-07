package xyz.haff.petclinic.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import xyz.haff.petclinic.models.Owner;
import xyz.haff.petclinic.models.forms.OwnerForm;

@Component
public class OwnerToOwnerFormConverter implements Converter<Owner, OwnerForm> {

    @Override
    public OwnerForm convert(Owner owner) {
        var personalData = owner.getPersonalData();

        return OwnerForm.builder()
                .firstName(personalData.getFirstName())
                .lastName(personalData.getLastName())
                .username(personalData.getUser().getUsername())
                .password("")
                .repeatPassword("")
                .build();
    }
}
