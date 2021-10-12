package xyz.haff.petclinic.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import xyz.haff.petclinic.exceptions.SpecificNotFoundException;
import xyz.haff.petclinic.models.User;
import xyz.haff.petclinic.repositories.OwnerRepository;

import java.util.UUID;

@RequiredArgsConstructor
@Component
public class UserAuthenticationManager {
    private final OwnerRepository ownerRepository;

    public boolean userMatches(Authentication authentication, UUID userId) {
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
