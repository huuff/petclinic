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
import xyz.haff.petclinic.repositories.VetRepository;
import xyz.haff.petclinic.security.UserDetailsAdapter;

@Controller
@RequiredArgsConstructor
@RequestMapping(ProfileController.PATH)
public class ProfileController {
    public static final String PATH = "/profile";

    private final OwnerRepository ownerRepository;
    private final VetRepository vetRepository;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public String view(@AuthenticationPrincipal UserDetailsAdapter userDetails, Model model) {
        if (userDetails.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("OWNER"))) {
            model.addAttribute("owner", ownerRepository.findByUserId(userDetails.getUser().getId()).orElseThrow(NotFoundException::new));
            return "owners/view";
        } else if (userDetails.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("VET"))) {
            model.addAttribute("vet", vetRepository.findByUserId(userDetails.getUser().getId()).orElseThrow(NotFoundException::new));
            return "vets/view";
        } else {
            throw new NotFoundException();
        }
    }
}
