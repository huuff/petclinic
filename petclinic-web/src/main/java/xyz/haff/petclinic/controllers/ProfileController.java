package xyz.haff.petclinic.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import xyz.haff.petclinic.exceptions.NotFoundException;
import xyz.haff.petclinic.repositories.OwnerRepository;
import xyz.haff.petclinic.security.UserDetailsAdapter;

@Controller
@RequiredArgsConstructor
@RequestMapping(ProfileController.PATH)
public class ProfileController {
    public static final String PATH = "/profile";

    private final OwnerRepository ownerRepository;

    // TODO: This for vets
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public String view(@AuthenticationPrincipal UserDetailsAdapter userDetails, Model model) {
        model.addAttribute("owner", ownerRepository.findByUserId(userDetails.getUser().getId()).orElseThrow(NotFoundException::new));
        return "owners/view";
    }
}
