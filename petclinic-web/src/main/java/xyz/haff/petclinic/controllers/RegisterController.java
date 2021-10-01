package xyz.haff.petclinic.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import xyz.haff.petclinic.LoginService;
import xyz.haff.petclinic.models.Owner;
import xyz.haff.petclinic.models.Role;
import xyz.haff.petclinic.models.forms.RegistrationForm;
import xyz.haff.petclinic.repositories.OwnerRepository;
import xyz.haff.petclinic.repositories.UserRepository;

@Controller
@RequiredArgsConstructor
@RequestMapping("/register")
public class RegisterController {
    private static final String TEMPLATE = "owners/register";

    private final LoginService loginService;
    private final OwnerRepository ownerRepository;
    private final UserRepository userRepository;
    private final Converter<RegistrationForm, Owner> registrationFormOwner;

    @GetMapping
    public String showForm(Model model) {
        model.addAttribute("registrationForm", new RegistrationForm());

        return TEMPLATE;
    }

    // TODO: Deal with first name and last name duplicates
    @PreAuthorize("permitAll()")
    @PostMapping
    public String register(@ModelAttribute RegistrationForm registrationForm, BindingResult bindingResult) {
        if (userRepository.existsByUsername(registrationForm.getUsername())) {
            bindingResult.rejectValue("username", "duplicate", new Object[]{registrationForm.getUsername()}, "");
            return TEMPLATE;
        }

        var owner = registrationFormOwner.convert(registrationForm);

        owner.getPersonalData().getUser().setRole(Role.OWNER);
        ownerRepository.save(owner);
        loginService.loginPerson(registrationForm.getUsername(), registrationForm.getPassword());

        return "redirect:/";
    }
}
