package xyz.haff.petclinic.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import xyz.haff.petclinic.exceptions.SpecificNotFoundException;
import xyz.haff.petclinic.models.Owner;
import xyz.haff.petclinic.models.forms.PetForm;
import xyz.haff.petclinic.repositories.OwnerRepository;
import xyz.haff.petclinic.repositories.PetRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PetFormValidationService {
    private final PetRepository petRepository;
    private final OwnerRepository ownerRepository;

    public void checkNameIsDuplicated(PetForm petForm, UUID ownerId, BindingResult bindingResult) {
        var owner =  ownerRepository.findById(ownerId).orElseThrow(() -> SpecificNotFoundException.fromOwnerId(ownerId));

        checkNameIsDuplicated(petForm, owner, bindingResult);
    }

    public void checkNameIsDuplicated(PetForm petForm, Owner owner, BindingResult bindingResult) {
        var isDuplicated = owner
                .getPets().stream()
                .anyMatch(pet -> pet.getName().equals(petForm.getName()));

        if (isDuplicated) {
            bindingResult.rejectValue("name", "duplicate", new Object[]{petForm.getName()}, "");
        }
    }

}
