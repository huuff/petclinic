package xyz.haff.petclinic.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import xyz.haff.petclinic.models.forms.RegistrationForm;
import xyz.haff.petclinic.repositories.OwnerRepository;
import xyz.haff.petclinic.repositories.PersonalDataRepository;
import xyz.haff.petclinic.repositories.UserRepository;

@RequiredArgsConstructor
@Service
public class OwnerService {
    private final UserRepository userRepository;
    private final PersonalDataRepository personalDataRepository;

    public boolean hasErrors(RegistrationForm registrationForm, BindingResult bindingResult) {
        personalDataRepository.findByFirstNameAndLastName(
                registrationForm.getFirstName(),
                registrationForm.getLastName()
        ).ifPresent((personalData) -> {
            bindingResult.reject("duplicate", new Object[]{personalData.fullName()}, "");
        });

        if (!registrationForm.passwordEqualsRepeatPassword())
            bindingResult.reject("repeat_password_error");

        if (userRepository.existsByUsername(registrationForm.getUsername()))
            bindingResult.rejectValue("username", "duplicate", new Object[]{registrationForm.getUsername()}, "");

        return bindingResult.hasErrors();
    }
}
