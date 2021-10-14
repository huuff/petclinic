package xyz.haff.petclinic.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import xyz.haff.petclinic.exceptions.SpecificNotFoundException;
import xyz.haff.petclinic.repositories.PetRepository;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping(PetController.BASE_PATH)
public class PetController {
    public static final String BASE_PATH = "/pets";
    public static final String READ_PATH = "/{petId}";
    public static final String DELETE_PATH = "/{petId}/delete";

    public static final String READ_VIEW = "/pets/view";

    private final PetRepository petRepository;

    @GetMapping(DELETE_PATH)
    public String delete(@PathVariable UUID petId) {
        var petToDelete = petRepository.findById(petId).orElseThrow(() -> SpecificNotFoundException.fromPetId(petId));

        petRepository.deleteById(petId);

        return OwnersController.redirectToOwnerView(petToDelete.getOwner().getId());
    }

    @GetMapping(READ_PATH)
    public String read(@PathVariable UUID petId, Model model) {
        var pet = petRepository.findById(petId).orElseThrow(() -> SpecificNotFoundException.fromPetId(petId));

        model.addAttribute("pet", pet);

        return READ_VIEW;
    }

}
