package xyz.haff.petclinic.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import xyz.haff.petclinic.converters.PetFormToPetConverter;
import xyz.haff.petclinic.models.Owner;
import xyz.haff.petclinic.models.Pet;
import xyz.haff.petclinic.models.forms.PetForm;
import xyz.haff.petclinic.repositories.OwnerRepository;


@Service
@RequiredArgsConstructor
public class PetService {
    private final PetFormToPetConverter petFormToPet;
    private final OwnerRepository ownerRepository;

    // TODO: Log
    public void createPet(Owner owner, PetForm petForm) {
        var pet = petFormToPet.convert(petForm);
        Assert.notNull(pet, "Converted pet cannot be null");
        pet.setOwner(owner);
        owner.getPets().add(pet);
        ownerRepository.save(owner);
    }
}
