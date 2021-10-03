package xyz.haff.petclinic.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import xyz.haff.petclinic.exceptions.NotFoundException;
import xyz.haff.petclinic.models.forms.RegistrationForm;
import xyz.haff.petclinic.repositories.OwnerRepository;
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
    public static final String DELETE = "/{ownerId}/delete";

    private static final String EDIT_VIEW = "owners/register";

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
    @PreAuthorize("hasAuthority('VET')")
    public String showCreateForm(Model model) {
        model.addAttribute("registrationForm", new RegistrationForm());

        return EDIT_VIEW;
    }
    @PostMapping(CREATE)
    @PreAuthorize("hasAuthority('VET')")
    public String create(@Valid @ModelAttribute RegistrationForm registrationForm, BindingResult bindingResult) {
        if (ownerService.hasErrors(registrationForm, bindingResult))
            return EDIT_VIEW;

        registerService.registerOwner(registrationForm);

        return "redirect:" + BASE_PATH;
    }

    @GetMapping(DELETE)
    @PreAuthorize("hasAuthority('VET')")
    public String delete(@PathVariable UUID ownerId) {
        ownerRepository.deleteById(ownerId);

        return "redirect:" + BASE_PATH;
    }

    // TODO: This multi-tenancy is actually useless, but I'm leaving around for when I implement the post (Update)
    @GetMapping("/{ownerId}")
    @PreAuthorize("hasAuthority('VET') OR @userAuthenticationManager.ownerUserMatches(authentication, #ownerId)")
    public String view(@PathVariable UUID ownerId, Model model) {
        model.addAttribute("owner", ownerRepository.findById(ownerId).orElseThrow(NotFoundException::new));

        return "owners/view";
    }
}
