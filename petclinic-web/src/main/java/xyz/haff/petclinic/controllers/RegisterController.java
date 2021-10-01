package xyz.haff.petclinic.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import xyz.haff.petclinic.LoginService;
import xyz.haff.petclinic.models.Owner;
import xyz.haff.petclinic.models.PersonalData;
import xyz.haff.petclinic.models.Role;
import xyz.haff.petclinic.models.User;
import xyz.haff.petclinic.models.forms.RegistrationForm;
import xyz.haff.petclinic.repositories.OwnerRepository;

@Controller
@RequiredArgsConstructor
@RequestMapping("/register")
public class RegisterController {
    private final LoginService loginService;
    private final OwnerRepository ownerRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping
    public String showForm(Model model) {
        model.addAttribute("registrationForm", new RegistrationForm());

        return "owners/register";
    }

    // TODO: Deal with duplicates
    @PreAuthorize("permitAll()")
    @PostMapping
    public String register(@ModelAttribute RegistrationForm registrationForm) {

        var owner = Owner.builder()
                .personalData(PersonalData.builder()
                        .firstName(registrationForm.getFirstName())
                        .lastName(registrationForm.getLastName())
                        .user(User.builder()
                                .username(registrationForm.getUsername())
                                .password(passwordEncoder.encode(registrationForm.getPassword()))
                                .role(Role.OWNER)
                                .build()
                        ).build()
                ).build();

        owner.getPersonalData().getUser().setRole(Role.OWNER);
        ownerRepository.save(owner);
        loginService.loginPerson(registrationForm.getUsername(), registrationForm.getPassword());

        return "redirect:/";
    }
}
