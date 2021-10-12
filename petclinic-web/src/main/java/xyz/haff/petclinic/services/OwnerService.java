package xyz.haff.petclinic.services;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;
import xyz.haff.petclinic.converters.OwnerToOwnerFormConverter;
import xyz.haff.petclinic.exceptions.SpecificNotFoundException;
import xyz.haff.petclinic.models.Owner;
import xyz.haff.petclinic.models.forms.OwnerForm;
import xyz.haff.petclinic.repositories.OwnerRepository;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class OwnerService {
    private final Converter<OwnerForm, Owner> ownerFormToOwner;
    private final OwnerRepository ownerRepository;
    private final OwnerToOwnerFormConverter ownerToOwnerFormConverter;

    public OwnerForm createForm(UUID ownerId) {
        return ownerToOwnerFormConverter.convert(ownerRepository.findById(ownerId)
                .orElseThrow(() -> SpecificNotFoundException.fromOwnerId(ownerId)));
    }

    public void updateOwner(UUID ownerId, OwnerForm ownerForm) {
        var owner = ownerRepository.findById(ownerId)
                .orElseThrow(() -> SpecificNotFoundException.fromOwnerId(ownerId));
        var personalData = owner.getPersonalData();

        if (!Strings.isEmpty(ownerForm.getFirstName()))
            personalData.setFirstName(ownerForm.getFirstName());

        if (!Strings.isEmpty(ownerForm.getLastName()))
            personalData.setLastName(ownerForm.getLastName());

        if (!Strings.isEmpty(ownerForm.getUsername()))
            personalData.getUser().setUsername(ownerForm.getUsername());

        if (!Strings.isEmpty(ownerForm.getPassword()) && ownerForm.passwordEqualsRepeatPassword())
            personalData.getUser().setPassword(ownerForm.getPassword());

        ownerRepository.save(owner);
    }

    public Owner register(OwnerForm ownerForm) {
        var owner = ownerFormToOwner.convert(ownerForm);
        return ownerRepository.save(owner);
    }


}
