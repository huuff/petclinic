package xyz.haff.petclinic.services;

import lombok.RequiredArgsConstructor;
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

    public boolean hasErrors(OwnerForm ownerForm, BindingResult bindingResult) {
        personalDataRepository.findByFirstNameAndLastName(
                ownerForm.getFirstName(),
                ownerForm.getLastName()
        ).ifPresent((personalData) -> {
            bindingResult.reject("duplicate", new Object[]{personalData.fullName()}, "");
        });

        if (!ownerForm.passwordEqualsRepeatPassword())
            bindingResult.reject("repeat_password_error");

        if (userRepository.existsByUsername(ownerForm.getUsername()))
            bindingResult.rejectValue("username", "duplicate", new Object[]{ownerForm.getUsername()}, "");

        return bindingResult.hasErrors();
    }

    public OwnerForm createOwnerForm(UUID ownerId) {
        return ownerToOwnerFormConverter.convert(ownerRepository.findById(ownerId).orElseThrow(NotFoundException::new));
    }

    public Owner updateOwner(UUID ownerId, OwnerForm ownerForm) {
        var owner = ownerRepository.findById(ownerId).orElseThrow(NotFoundException::new);
        var personalData = owner.getPersonalData();

        personalData.setFirstName(ownerForm.getFirstName());
        personalData.setLastName(ownerForm.getLastName());
        personalData.getUser().setUsername(ownerForm.getUsername());

        if (ownerForm.getPassword() != null && !ownerForm.getPassword().equals(""))
            personalData.getUser().setPassword(ownerForm.getPassword());

        return ownerRepository.save(owner);
    }
}
