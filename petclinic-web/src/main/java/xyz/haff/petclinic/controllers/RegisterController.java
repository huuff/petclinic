package xyz.haff.petclinic.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import xyz.haff.petclinic.LoginService;
import xyz.haff.petclinic.models.Owner;
import xyz.haff.petclinic.models.Role;
import xyz.haff.petclinic.repositories.OwnerRepository;

@Controller
@RequiredArgsConstructor
@RequestMapping("/register")
public class RegisterController {
    private final LoginService loginService;
    private final OwnerRepository ownerRepository;

    @GetMapping
    public String showForm(Model model) {
        model.addAttribute("owner", Owner.builder().build());

        return "owners/register";
    }

    // TODO: Use form object
    // TODO: Deal with duplicates
    // TODO: Hash password
    @PreAuthorize("permitAll()")
    @PostMapping
    public String register(@ModelAttribute Owner owner) {
        owner.getPersonalData().getUser().setRole(Role.OWNER);
        ownerRepository.save(owner);
        loginService.loginPerson(owner);

        return "redirect:/";
    }
}
