package xyz.haff.petclinic.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import xyz.haff.petclinic.exceptions.GenericNotFoundException;
import xyz.haff.petclinic.exceptions.SpecificNotFoundException;
import xyz.haff.petclinic.models.Role;
import xyz.haff.petclinic.repositories.OwnerRepository;
import xyz.haff.petclinic.repositories.VetRepository;
import xyz.haff.petclinic.security.UserDetailsAdapter;
import xyz.haff.petclinic.services.TitleService;
import xyz.haff.petclinic.services.VisitService;

@Controller
@RequiredArgsConstructor
@RequestMapping(ProfileController.PATH)
public class ProfileController {
    public static final String PATH = "/profile";

    private final OwnerRepository ownerRepository;
    private final VetRepository vetRepository;
    private final TitleService titleService;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public String view(@AuthenticationPrincipal Object authenticationPrincipal, Model model) {
        if (!(authenticationPrincipal instanceof UserDetailsAdapter))
            throw new GenericNotFoundException();

        var userDetails = (UserDetailsAdapter) authenticationPrincipal;

        if (userDetails.getUser().getRole().equals(Role.OWNER)) {
            var owner = ownerRepository.findByUserId(userDetails.getUser().getId())
                    .orElseThrow(() -> SpecificNotFoundException.fromUserId(userDetails.getUser().getId()));

            titleService.person(model, owner.getPersonalData());

            model.addAttribute("owner", owner);
            return "owners/view";
        } else if (userDetails.getUser().getRole().equals(Role.VET)) {
            var vet = vetRepository.findByUserId(userDetails.getUser().getId())
                    .orElseThrow(() -> SpecificNotFoundException.fromUserId(userDetails.getUser().getId()));

            titleService.person(model, vet.getPersonalData());

            model.addAttribute("vet", vet);
            return "vets/view";
        } else {
            throw new GenericNotFoundException();
        }
    }
}
