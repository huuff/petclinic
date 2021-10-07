package xyz.haff.petclinic.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import xyz.haff.petclinic.exceptions.NotFoundException;
import xyz.haff.petclinic.models.forms.VetForm;
import xyz.haff.petclinic.repositories.PersonalDataRepository;
import xyz.haff.petclinic.repositories.UserRepository;
import xyz.haff.petclinic.repositories.VetRepository;

import java.util.UUID;

// TODO: Mostly copy-pasted from the OwnerService, so I have to find a way to DRY them

@RequiredArgsConstructor
@Service
public class VetService {
    private final UserRepository userRepository;
    private final PersonalDataRepository personalDataRepository;
    private final VetRepository vetRepository;

    public boolean checkNewIsValid(VetForm vetForm, BindingResult bindingResult) {
        checkFullNameIsNotDuplicated(vetForm, bindingResult);
        checkPasswordsMatch(vetForm, bindingResult);
        checkUsernameIsNotDuplicated(vetForm, bindingResult);

        return !bindingResult.hasErrors();
    }

    public void checkEditIsValid(UUID id, VetForm vetForm, BindingResult bindingResult) {
        var editingVet = vetRepository.findById(id).orElseThrow(NotFoundException::new);

        checkPasswordsMatch(vetForm, bindingResult);
        if (!editingVet.getPersonalData().getFirstName().equals(vetForm.getFirstName()))
            checkFullNameIsNotDuplicated(vetForm, bindingResult);

        if (!editingVet.getPersonalData().getUser().getUsername().equals(vetForm.getUsername()))
            checkUsernameIsNotDuplicated(vetForm, bindingResult);
    }

    public void checkFullNameIsNotDuplicated(VetForm vetForm, BindingResult bindingResult) {
        var existingDuplicate = personalDataRepository.findByFirstNameAndLastName(
                vetForm.getFirstName(),
                vetForm.getLastName()
        );
        existingDuplicate.ifPresent(personalData -> bindingResult.reject("duplicate", new Object[]{personalData.fullName()}, ""));
    }

    public void checkUsernameIsNotDuplicated(VetForm vetForm, BindingResult bindingResult) {
        if (userRepository.existsByUsername(vetForm.getUsername()))
            bindingResult.rejectValue("username", "duplicate", new Object[]{vetForm.getUsername()}, "");
    }

    public void checkPasswordsMatch(VetForm vetForm, BindingResult bindingResult) {
        if (!vetForm.passwordEqualsRepeatPassword())
            bindingResult.reject("repeat_password_error");
    }
}
