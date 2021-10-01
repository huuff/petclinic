package xyz.haff.petclinic.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import xyz.haff.petclinic.models.forms.RegistrationForm;
import xyz.haff.petclinic.repositories.OwnerRepository;
import xyz.haff.petclinic.services.OwnerService;
import xyz.haff.petclinic.services.RegisterService;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping(OwnersController.BASE_PATH)
public class OwnersController {
    public static final String BASE_PATH = "/owners";
    public static final String CREATE = "/create";

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

        return "owners/register"; // TODO: Maybe some static constants for the view location? or properties?
    }
    @PostMapping(CREATE)
    @PreAuthorize("hasAuthority('VET')")
    public String create(@Valid @ModelAttribute RegistrationForm registrationForm, BindingResult bindingResult) {
        if (ownerService.hasErrors(registrationForm, bindingResult))
            return "owners/register";

        registerService.registerOwner(registrationForm);

        return "redirect:" + BASE_PATH;
    }
}
