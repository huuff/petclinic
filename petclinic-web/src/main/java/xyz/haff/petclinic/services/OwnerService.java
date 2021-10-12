package xyz.haff.petclinic.services;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import xyz.haff.petclinic.converters.OwnerToOwnerFormConverter;
import xyz.haff.petclinic.exceptions.NotFoundException;
import xyz.haff.petclinic.models.Owner;
import xyz.haff.petclinic.models.forms.OwnerForm;
import xyz.haff.petclinic.repositories.OwnerRepository;
import xyz.haff.petclinic.repositories.PersonalDataRepository;
import xyz.haff.petclinic.repositories.UserRepository;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class OwnerService {
    private final PersonFormValidationService personFormValidationService;
    private final OwnerRepository ownerRepository;
    private final OwnerToOwnerFormConverter ownerToOwnerFormConverter;

    public OwnerForm createOwnerForm(UUID ownerId) {
        return ownerToOwnerFormConverter.convert(ownerRepository.findById(ownerId).orElseThrow(NotFoundException::new));
    }

    public void updateOwner(UUID ownerId, OwnerForm ownerForm) {
        var owner = ownerRepository.findById(ownerId).orElseThrow(NotFoundException::new);
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


}
