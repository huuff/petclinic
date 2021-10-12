package xyz.haff.petclinic.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import xyz.haff.petclinic.models.PersonalData;
import xyz.haff.petclinic.models.forms.PersonForm;
import xyz.haff.petclinic.repositories.PersonalDataRepository;
import xyz.haff.petclinic.repositories.UserRepository;

@Service
@RequiredArgsConstructor
public class PersonFormValidationService {
    private final UserRepository userRepository;
    private final PersonalDataRepository personalDataRepository;

    public void checkPasswordsMatch(PersonForm personForm, BindingResult bindingResult) {
        if (!personForm.passwordEqualsRepeatPassword())
            bindingResult.reject("repeat_password_error");
    }

    public void checkUsernameIsNotDuplicated(PersonForm personForm, BindingResult bindingResult) {
        if (userRepository.existsByUsername(personForm.getUsername()))
            bindingResult.rejectValue("username", "duplicate", new Object[]{personForm.getUsername()}, "");
    }

    public void checkFullNameIsNotDuplicated(PersonForm vetForm, BindingResult bindingResult) {
        var existingDuplicate = personalDataRepository.findByFirstNameAndLastName(
                vetForm.getFirstName(),
                vetForm.getLastName()
        );
        existingDuplicate.ifPresent(personalData -> bindingResult.reject("duplicate", new Object[]{personalData.fullName()}, ""));
    }

    @SuppressWarnings({"BooleanMethodIsAlwaysInverted", "UnusedReturnValue"})
    public boolean checkEditIsValid(PersonalData editingPerson, PersonForm personForm, BindingResult bindingResult) {
        checkPasswordsMatch(personForm, bindingResult);
        if (!editingPerson.getFirstName().equals(personForm.getFirstName()))
            checkFullNameIsNotDuplicated(personForm, bindingResult);

        if (!editingPerson.getUser().getUsername().equals(personForm.getUsername()))
            checkUsernameIsNotDuplicated(personForm, bindingResult);

        return !bindingResult.hasErrors();
    }


    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean checkNewIsValid(PersonForm vetForm, BindingResult bindingResult) {
        checkFullNameIsNotDuplicated(vetForm, bindingResult);
        checkPasswordsMatch(vetForm, bindingResult);
        checkUsernameIsNotDuplicated(vetForm, bindingResult);

        return !bindingResult.hasErrors();
    }
}
