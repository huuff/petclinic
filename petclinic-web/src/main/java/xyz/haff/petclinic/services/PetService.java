package xyz.haff.petclinic.services;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import xyz.haff.petclinic.converters.PetFormToPetConverter;
import xyz.haff.petclinic.models.Owner;
import xyz.haff.petclinic.models.Pet;
import xyz.haff.petclinic.models.forms.PetForm;
import xyz.haff.petclinic.repositories.OwnerRepository;
import xyz.haff.petclinic.repositories.PetRepository;


@Service
@RequiredArgsConstructor
public class PetService {
    private final PetFormToPetConverter petFormToPet;
    private final PetRepository petRepository;
    private final OwnerRepository ownerRepository;

    // TODO: Log
    @Transactional
    public Pet createPet(Owner owner, PetForm petForm) {
        var pet = petFormToPet.convert(petForm);
        Assert.notNull(pet, "Converted pet cannot be null");
        pet.setOwner(owner);
        owner.getPets().add(pet);
        var savedPet = petRepository.save(pet);
        ownerRepository.save(owner);

        return savedPet;
    }

    public void updatePet(Pet editingPet, PetForm petForm) {
        if (!Strings.isEmpty(petForm.getName()))
            editingPet.setName(petForm.getName());
        if (petForm.getBirthDate() != null)
            editingPet.setBirthDate(petForm.getBirthDate());
        if (petForm.getType() != null)
            editingPet.setType(petForm.getType());

        petRepository.save(editingPet);
    }

}
