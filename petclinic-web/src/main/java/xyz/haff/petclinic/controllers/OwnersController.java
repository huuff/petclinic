package xyz.haff.petclinic.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xyz.haff.petclinic.annotations.EditOwner;
import xyz.haff.petclinic.exceptions.SpecificNotFoundException;
import xyz.haff.petclinic.models.forms.CreationConstraintGroup;
import xyz.haff.petclinic.models.forms.OwnerForm;
import xyz.haff.petclinic.models.forms.PetForm;
import xyz.haff.petclinic.repositories.OwnerRepository;
import xyz.haff.petclinic.security.UserDetailsAdapter;
import xyz.haff.petclinic.services.*;

import javax.validation.Valid;
import java.util.UUID;

import static xyz.haff.petclinic.controllers.ControllerUtil.redirect;

@Controller
@RequiredArgsConstructor
@RequestMapping(OwnersController.BASE_PATH)
public class OwnersController {
    public static final String BASE_PATH = "/owners";
    public static final String CREATE = "/create";
    public static final String UPDATE = "/{ownerId}/update";
    public static final String DELETE = "/{ownerId}/delete";
    public static final String CREATE_PET_PATH = "/{ownerId}/pets/create";

    private static final String EDIT_VIEW = "owners/edit";
    private static final String PET_EDIT_VIEW = "pets/edit"; // TODO: Maybe should be a variable in PetController?

    private final OwnerRepository ownerRepository;
    private final OwnerService ownerService;
    private final PetService petService;
    private final PetFormValidationService petFormValidationService;
    private final LoginService loginService;
    private final PersonFormValidationService personFormValidationService;

    @GetMapping
    @PreAuthorize("hasAuthority('VET')")
    public String list(Model model) {
        model.addAttribute("owners", ownerRepository.findAll());

        return "owners/list";
    }

    @GetMapping(CREATE)
    @PreAuthorize("!hasAuthority('OWNER')")
    public String showCreateForm(Model model) {
        model.addAttribute("ownerForm", new OwnerForm());

        return EDIT_VIEW;
    }
    @PostMapping(CREATE)
    @PreAuthorize("!hasAuthority('OWNER')")
    public String create(
            @AuthenticationPrincipal UserDetailsAdapter user,
            @Validated(CreationConstraintGroup.class) @ModelAttribute OwnerForm ownerForm,
            BindingResult bindingResult
    ) {
        if (!personFormValidationService.checkNewIsValid(ownerForm, bindingResult))
            return EDIT_VIEW;

        var newOwner = ownerService.register(ownerForm);

        if (user == null) {// not authenticated, so it's a registration, redirect to home
            loginService.login(newOwner);
            return "redirect:/";
        } else { // must be a vet creating an owner, redirect to owners list
            return "redirect:" + BASE_PATH;
        }
    }

    @GetMapping(DELETE)
    @PreAuthorize("hasAuthority('VET')")
    public String delete(@PathVariable UUID ownerId) {
        ownerRepository.deleteById(ownerId);

        return "redirect:" + BASE_PATH;
    }

    @GetMapping("/{ownerId}")
    @EditOwner
    public String view(@PathVariable UUID ownerId, Model model) {
        model.addAttribute("owner", ownerRepository.findById(ownerId)
                .orElseThrow(() -> SpecificNotFoundException.fromOwnerId(ownerId)));

        return "owners/view";
    }

    @GetMapping(UPDATE)
    @EditOwner
    public String showEditForm(@PathVariable UUID ownerId, Model model) {
        model.addAttribute("ownerForm", ownerService.createForm(ownerId));

        return EDIT_VIEW;
    }

    @PostMapping(UPDATE)
    @EditOwner
    public String edit(@PathVariable UUID ownerId, @Valid OwnerForm ownerForm, BindingResult bindingResult) {
        var editingPerson = ownerRepository.findById(ownerId)
                .orElseThrow(() -> SpecificNotFoundException.fromOwnerId(ownerId))
                .getPersonalData();

        personFormValidationService.checkEditIsValid(editingPerson, ownerForm, bindingResult);

        if (bindingResult.hasErrors())
            return EDIT_VIEW;

        ownerService.updateOwner(ownerId, ownerForm);

        return redirectToOwnerView(ownerId);
    }

    @GetMapping(CREATE_PET_PATH)
    @EditOwner
    public String showCreatePetForm(@PathVariable UUID ownerId, Model model) {
        if (!ownerRepository.existsById(ownerId))
            throw SpecificNotFoundException.fromOwnerId(ownerId);

        model.addAttribute("petForm", new PetForm());

        return "pets/edit";
    }

    @PostMapping(CREATE_PET_PATH)
    @EditOwner
    public String createPet(@PathVariable UUID ownerId, @Validated(CreationConstraintGroup.class) PetForm petForm, BindingResult bindingResult) {
        petFormValidationService.checkNameIsDuplicated(petForm, ownerId, bindingResult);

        if (bindingResult.hasErrors())
            return PET_EDIT_VIEW;
        else {
            var owner = ownerRepository.findById(ownerId).orElseThrow(() -> SpecificNotFoundException.fromOwnerId(ownerId));
            var savedPet = petService.createPet(owner, petForm);

            return redirect(PetController.viewPetUrl(savedPet.getId()));
        }
    }

    public static String redirectToOwnerView(UUID ownerId) {
        return "redirect:" + BASE_PATH + "/" + ownerId;
    }

}
