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
import xyz.haff.petclinic.exceptions.NotFoundException;
import xyz.haff.petclinic.models.forms.OwnerForm;
import xyz.haff.petclinic.models.forms.PersonForm;
import xyz.haff.petclinic.repositories.OwnerRepository;
import xyz.haff.petclinic.security.UserDetailsAdapter;
import xyz.haff.petclinic.services.OwnerService;
import xyz.haff.petclinic.services.RegisterService;

import javax.validation.Valid;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping(OwnersController.BASE_PATH)
public class OwnersController {
    public static final String BASE_PATH = "/owners";
    public static final String CREATE = "/create";
    public static final String UPDATE = "/{ownerId}/update";
    public static final String DELETE = "/{ownerId}/delete";

    private static final String EDIT_VIEW = "owners/edit";

    private final OwnerRepository ownerRepository;
    private final OwnerService ownerService;
    private final RegisterService registerService;

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
            @Validated(PersonForm.CreationConstraintGroup.class) @ModelAttribute OwnerForm ownerForm,
            BindingResult bindingResult
    ) {
        if (!ownerService.checkNewIsValid(ownerForm, bindingResult))
            return EDIT_VIEW;

        var newOwner = registerService.registerOwner(ownerForm);

        if (user == null) {// not authenticated, so it's a registration, redirect to home
            registerService.login(newOwner);
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

    // TODO: None of these is secured!

    @GetMapping("/{ownerId}")
    public String view(@PathVariable UUID ownerId, Model model) {
        model.addAttribute("owner", ownerRepository.findById(ownerId).orElseThrow(NotFoundException::new));

        return "owners/view";
    }

    @GetMapping(UPDATE)
    @EditOwner
    public String showEditForm(@PathVariable UUID ownerId, Model model) {
        model.addAttribute("ownerForm", ownerService.createOwnerForm(ownerId));

        return EDIT_VIEW;
    }

    @PostMapping(UPDATE)
    @EditOwner
    public String edit(@PathVariable UUID ownerId, @Valid OwnerForm ownerForm, BindingResult bindingResult) {
        ownerService.checkEditIsValid(ownerId, ownerForm, bindingResult);

        if (bindingResult.hasErrors())
            return EDIT_VIEW;

        ownerService.updateOwner(ownerId, ownerForm);

        return "redirect:" + BASE_PATH + "/" + ownerId;
    }

}
