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
import java.util.Locale;
import java.util.UUID;

import static xyz.haff.petclinic.controllers.ControllerUtil.redirect;

// TODO: This controller is getting really large, consider extracting services

@Controller
@RequiredArgsConstructor
@RequestMapping(OwnersController.BASE_PATH)
public class OwnersController {
    public static final String BASE_PATH = "/owners";
    public static final String CREATE = "/create";
    public static final String UPDATE = "/{ownerId}/update";
    public static final String DELETE = "/{ownerId}/delete";
    public static final String PETS_PATH = "/{ownerId}/pets";
    public static final String CREATE_PET_PATH = PETS_PATH + "/create";

    private static final String EDIT_VIEW = "owners/edit";

    private final OwnerRepository ownerRepository;
    private final OwnerService ownerService;
    private final PetService petService;
    private final PetFormValidationService petFormValidationService;
    private final LoginService loginService;
    private final PersonFormValidationService personFormValidationService;
    private final TitleService titleService;

    @GetMapping
    @PreAuthorize("hasAuthority('VET')")
    public String list(Model model, Locale locale) {
        titleService.listOwners(model, locale);
        model.addAttribute("owners", ownerRepository.findAll());

        return "owners/list";
    }

    @GetMapping(CREATE)
    @PreAuthorize("!hasAuthority('OWNER')")
    public String showCreateForm(Model model, Locale locale) {
        model.addAttribute("ownerForm", new OwnerForm());
        titleService.newOwner(model, locale);

        return EDIT_VIEW;
    }
    @PostMapping(CREATE)
    @PreAuthorize("!hasAuthority('OWNER')")
    public String create(
            @AuthenticationPrincipal UserDetailsAdapter user,
            @Validated(CreationConstraintGroup.class) @ModelAttribute OwnerForm ownerForm,
            BindingResult bindingResult,
            Model model,
            Locale locale
    ) {
        if (!personFormValidationService.checkNewIsValid(ownerForm, bindingResult)) {
            titleService.newOwner(model, locale);
            return EDIT_VIEW;
        }

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
        var owner = ownerRepository.findById(ownerId).orElseThrow(() -> SpecificNotFoundException.fromOwnerId(ownerId));

        titleService.person(model, owner.getPersonalData());
        model.addAttribute("owner", owner);

        return "owners/view";
    }

    @GetMapping(UPDATE)
    @EditOwner
    public String showEditForm(@PathVariable UUID ownerId, Model model) {
        var owner = ownerRepository.findById(ownerId).orElseThrow(() -> SpecificNotFoundException.fromOwnerId(ownerId));

        model.addAttribute("title", owner.getPersonalData().fullName());
        model.addAttribute("ownerForm", ownerService.createForm(owner));

        return EDIT_VIEW;
    }

    @PostMapping(UPDATE)
    @EditOwner
    public String edit(@PathVariable UUID ownerId, @Valid OwnerForm ownerForm, BindingResult bindingResult, Model model) {
        var editingPerson = ownerRepository.findById(ownerId)
                .orElseThrow(() -> SpecificNotFoundException.fromOwnerId(ownerId))
                .getPersonalData();

        personFormValidationService.checkEditIsValid(editingPerson, ownerForm, bindingResult);

        if (bindingResult.hasErrors()) {
            titleService.person(model, editingPerson);
            return EDIT_VIEW;
        }

        ownerService.updateOwner(ownerId, ownerForm);

        return redirectToOwnerView(ownerId);
    }

    @GetMapping(PETS_PATH)
    @EditOwner
    public String listPets(@PathVariable UUID ownerId, Model model, Locale locale) {
        var owner = ownerRepository.findById(ownerId).orElseThrow(() -> SpecificNotFoundException.fromOwnerId(ownerId));

        titleService.listPets(model, locale);
        model.addAttribute("owner", owner);

        return "pets/list";
    }

    @GetMapping(CREATE_PET_PATH)
    @EditOwner
    public String showCreatePetForm(@PathVariable UUID ownerId, Model model, Locale locale) {
        if (!ownerRepository.existsById(ownerId))
            throw SpecificNotFoundException.fromOwnerId(ownerId);

        titleService.newPet(model, locale);
        model.addAttribute("petForm", new PetForm());

        return PetController.EDIT_VIEW;
    }

    @PostMapping(CREATE_PET_PATH)
    @EditOwner
    public String createPet(
            @PathVariable UUID ownerId,
            @Validated(CreationConstraintGroup.class) PetForm petForm,
            BindingResult bindingResult,
            Model model,
            Locale locale
    ) {
        petFormValidationService.checkNameIsDuplicated(petForm, ownerId, bindingResult);

        if (bindingResult.hasErrors()) {
            titleService.newPet(model, locale);
            return PetController.EDIT_VIEW;
        } else {
            var owner = ownerRepository.findById(ownerId).orElseThrow(() -> SpecificNotFoundException.fromOwnerId(ownerId));
            var savedPet = petService.createPet(owner, petForm);

            return redirect(PetController.viewPetUrl(savedPet.getId()));
        }
    }

    public static String redirectToOwnerView(UUID ownerId) {
        return "redirect:" + BASE_PATH + "/" + ownerId;
    }

}
