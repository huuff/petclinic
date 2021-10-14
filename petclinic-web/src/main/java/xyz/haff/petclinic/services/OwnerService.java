package xyz.haff.petclinic.services;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;
import xyz.haff.petclinic.exceptions.SpecificNotFoundException;
import xyz.haff.petclinic.models.Owner;
import xyz.haff.petclinic.models.forms.OwnerForm;
import xyz.haff.petclinic.repositories.OwnerRepository;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class OwnerService {
    private final Converter<OwnerForm, Owner> ownerFormToOwner;
    private final Converter<Owner, OwnerForm> ownerToOwnerFormConverter;
    private final OwnerRepository ownerRepository;
    private final PersonalDataService personalDataService;

    public OwnerForm createForm(UUID ownerId) {
        return ownerToOwnerFormConverter.convert(ownerRepository.findById(ownerId)
                .orElseThrow(() -> SpecificNotFoundException.fromOwnerId(ownerId)));
    }

    public void updateOwner(UUID ownerId, OwnerForm ownerForm) {
        var owner = ownerRepository.findById(ownerId)
                .orElseThrow(() -> SpecificNotFoundException.fromOwnerId(ownerId));
        owner.setPersonalData(personalDataService.getUpdated(owner.getPersonalData(), ownerForm));

        ownerRepository.save(owner);
    }

    public Owner register(OwnerForm ownerForm) {
        var owner = ownerFormToOwner.convert(ownerForm);
        return ownerRepository.save(owner);
    }


}
