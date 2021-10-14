package xyz.haff.petclinic.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import xyz.haff.petclinic.exceptions.SpecificNotFoundException;
import xyz.haff.petclinic.models.User;
import xyz.haff.petclinic.repositories.OwnerRepository;

import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Component
public class UserAuthenticationManager {
    private final OwnerRepository ownerRepository;

    public boolean userMatches(Authentication authentication, UUID userId) {
        if (!(authentication.getPrincipal() instanceof UserDetailsAdapter))
            return false;

        log.debug("Checking whether " + authentication.getPrincipal() + " is the username of user " + userId);

        User authenticatedUser = ((UserDetailsAdapter) authentication.getPrincipal()).getUser();

        return authenticatedUser.getId().equals(userId);
    }

    public boolean ownerUserMatches(Authentication authentication, UUID ownerId) {
        var userId = ownerRepository.findById(ownerId)
                .orElseThrow(() -> SpecificNotFoundException.fromOwnerId(ownerId))
                .getPersonalData().getUser().getId();

        return userMatches(authentication, userId);
    }
}
