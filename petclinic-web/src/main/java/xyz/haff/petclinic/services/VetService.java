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
// UPDATE, better now, but try to merge the checkEditIsValid

@RequiredArgsConstructor
@Service
public class VetService {
    private final PersonFormValidationService personFormValidationService;
    private final VetRepository vetRepository;


    public void checkEditIsValid(UUID id, VetForm vetForm, BindingResult bindingResult) {
        var editingVet = vetRepository.findById(id).orElseThrow(NotFoundException::new);

        personFormValidationService.checkPasswordsMatch(vetForm, bindingResult);
        if (!editingVet.getPersonalData().getFirstName().equals(vetForm.getFirstName()))
            personFormValidationService.checkFullNameIsNotDuplicated(vetForm, bindingResult);

        if (!editingVet.getPersonalData().getUser().getUsername().equals(vetForm.getUsername()))
            personFormValidationService.checkUsernameIsNotDuplicated(vetForm, bindingResult);
    }

}
