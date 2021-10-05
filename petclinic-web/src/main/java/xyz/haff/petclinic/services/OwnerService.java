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
    private final UserRepository userRepository;
    private final PersonalDataRepository personalDataRepository;
    private final OwnerRepository ownerRepository;
    private final OwnerToOwnerFormConverter ownerToOwnerFormConverter;

    public boolean checkNewIsValid(OwnerForm ownerForm, BindingResult bindingResult) {
        checkFullNameIsNotDuplicated(ownerForm, bindingResult);
        checkPasswordsMatch(ownerForm, bindingResult);
        checkUsernameIsNotDuplicated(ownerForm, bindingResult);

        return !bindingResult.hasErrors();
    }

    public void checkEditIsValid(UUID id, OwnerForm ownerForm, BindingResult bindingResult) {
        var editingOwner = ownerRepository.findById(id).orElseThrow(NotFoundException::new);

        checkPasswordsMatch(ownerForm, bindingResult);
        if (!editingOwner.getPersonalData().getFirstName().equals(ownerForm.getFirstName()))
            checkFullNameIsNotDuplicated(ownerForm, bindingResult);

        if (!editingOwner.getPersonalData().getUser().getUsername().equals(ownerForm.getUsername()))
            checkUsernameIsNotDuplicated(ownerForm, bindingResult);
    }

    public void checkFullNameIsNotDuplicated(OwnerForm ownerForm, BindingResult bindingResult) {
        var existingDuplicate = personalDataRepository.findByFirstNameAndLastName(
                ownerForm.getFirstName(),
                ownerForm.getLastName()
        );
        existingDuplicate.ifPresent(personalData -> bindingResult.reject("duplicate", new Object[]{personalData.fullName()}, ""));
    }

    public void checkUsernameIsNotDuplicated(OwnerForm ownerForm, BindingResult bindingResult) {
        if (userRepository.existsByUsername(ownerForm.getUsername()))
            bindingResult.rejectValue("username", "duplicate", new Object[]{ownerForm.getUsername()}, "");
    }

    public void checkPasswordsMatch(OwnerForm ownerForm, BindingResult bindingResult) {
        if (!ownerForm.passwordEqualsRepeatPassword())
            bindingResult.reject("repeat_password_error");
    }

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
