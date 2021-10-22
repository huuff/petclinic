package xyz.haff.petclinic.controllers.global;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import xyz.haff.petclinic.exceptions.SpecificNotFoundException;
import xyz.haff.petclinic.models.Role;
import xyz.haff.petclinic.repositories.OwnerRepository;
import xyz.haff.petclinic.repositories.PersonalDataRepository;
import xyz.haff.petclinic.repositories.VetRepository;
import xyz.haff.petclinic.security.UserDetailsAdapter;

/**
 * Adds the current logged in owner or vet as in authOwner or authVet, and the logged person as in authPerson
 */

@ControllerAdvice
@RequiredArgsConstructor
public class AddAuthPersonController {
    private final PersonalDataRepository personalDataRepository;
    private final OwnerRepository ownerRepository;
    private final VetRepository vetRepository;

    /**
     * Gotta receive an object because Spring Security sends "anonymousUser" for unauthenticated users, which is...
     * suboptimal
     * @param authPrincipal userDetailsAdapter for an authenticated user and anonymousUser for unauthenticated
     * @param model where authPerson will be added and also authVet or authOwner
     */
    @ModelAttribute
    public void addPerson(@AuthenticationPrincipal Object authPrincipal, Model model) {
        if (authPrincipal instanceof UserDetailsAdapter) {
            var userDetails = (UserDetailsAdapter) authPrincipal;
            var userId = userDetails.getUser().getId();
            var personalData = personalDataRepository
                    .findByUserId(userId)
                    .orElseThrow(() -> SpecificNotFoundException.fromUserId(userId));
            model.addAttribute("authPerson", personalData);

            if (userDetails.getUser().getRole().equals(Role.OWNER)) {
                var owner = ownerRepository
                        .findByUserId(userId)
                        .orElseThrow(() -> SpecificNotFoundException.fromUserId(userId));
                model.addAttribute("authOwner", owner);
            } else if (userDetails.getUser().getRole().equals(Role.VET)) {
                var vet = vetRepository
                        .findByUserId(userId)
                        .orElseThrow(() -> SpecificNotFoundException.fromUserId(userId));
                model.addAttribute("authVet", vet);
            }
        }
    }
}
