package xyz.haff.petclinic.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import xyz.haff.petclinic.annotations.EditPet;
import xyz.haff.petclinic.converters.PetToPetFormConverter;
import xyz.haff.petclinic.exceptions.SpecificNotFoundException;
import xyz.haff.petclinic.models.forms.PetForm;
import xyz.haff.petclinic.repositories.OwnerRepository;
import xyz.haff.petclinic.repositories.PetRepository;
import xyz.haff.petclinic.security.UserDetailsAdapter;
import xyz.haff.petclinic.services.PetFormValidationService;
import xyz.haff.petclinic.services.PetService;
import xyz.haff.petclinic.services.TitleService;

import java.util.Locale;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping(PetController.BASE_PATH)
public class PetController {
    public static final String BASE_PATH = "/pets";
    public static final String READ_PATH = "/{petId}";
    public static final String DELETE_PATH = "/{petId}/delete";
    public static final String UPDATE_PATH = "/{petId}/update";

    public static final String READ_VIEW = "/pets/view";
    public static final String EDIT_VIEW = "/pets/edit";

    private final PetRepository petRepository;
    private final PetToPetFormConverter petToPetForm;
    private final PetFormValidationService petFormValidationService;
    private final PetService petService;
    private final TitleService titleService;

    private final OwnerRepository ownerRepository;

    // TODO: This shows pets for an owner, add one that shows all pets for all owners for a vet
    @GetMapping()
    @PreAuthorize("hasAuthority('OWNER')")
    public String viewPets(@AuthenticationPrincipal UserDetailsAdapter userDetails, Model model, Locale locale) {
        titleService.listPets(model, locale);
        var userId = userDetails.getUser().getId();

        var owner = ownerRepository.findByUserId(userId).orElseThrow(() -> SpecificNotFoundException.fromUserId(userId));
        model.addAttribute("owner", owner);

        return "pets/list";
    }

    @GetMapping(DELETE_PATH)
    @EditPet
    public String delete(@PathVariable UUID petId) {
        var petToDelete = petRepository.findById(petId).orElseThrow(() -> SpecificNotFoundException.fromPetId(petId));

        petRepository.deleteById(petId);

        return OwnersController.redirectToOwnerView(petToDelete.getOwner().getId());
    }

    @GetMapping(READ_PATH)
    @EditPet
    public String read(@PathVariable UUID petId, Model model) {
        var pet = petRepository.findById(petId).orElseThrow(() -> SpecificNotFoundException.fromPetId(petId));

        titleService.pet(model, pet);
        model.addAttribute("pet", pet);

        return READ_VIEW;
    }

    @GetMapping(UPDATE_PATH)
    @EditPet
    public String showUpdateForm(@PathVariable UUID petId, Model model) {
        var petToUpdate = petRepository.findById(petId).orElseThrow(() -> SpecificNotFoundException.fromPetId(petId));

        titleService.pet(model, petToUpdate);
        model.addAttribute("petForm", petToPetForm.convert(petToUpdate));

        return EDIT_VIEW;
    }

    @PostMapping(UPDATE_PATH)
    @EditPet
    public String update(@PathVariable UUID petId, @Validated PetForm petForm, BindingResult bindingResult, Model model) {
        var editingPet = petRepository.findById(petId).orElseThrow(() -> SpecificNotFoundException.fromPetId(petId));
        var owner = editingPet.getOwner();

        if (!editingPet.getName().equals(petForm.getName()))
            petFormValidationService.checkNameIsDuplicated(petForm, owner, bindingResult);

        if (bindingResult.hasErrors()) {
            titleService.pet(model, editingPet);
            return EDIT_VIEW;
        } else {
            petService.updatePet(editingPet, petForm);

            return "redirect:" + viewPetUrl(petId);
        }
    }

    public static String viewPetUrl(UUID petId) {
        return BASE_PATH + "/" + petId;
    }

}
