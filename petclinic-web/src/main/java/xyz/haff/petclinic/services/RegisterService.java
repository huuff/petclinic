package xyz.haff.petclinic.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import xyz.haff.petclinic.models.Owner;
import xyz.haff.petclinic.models.Role;
import xyz.haff.petclinic.models.forms.OwnerForm;
import xyz.haff.petclinic.repositories.OwnerRepository;
import xyz.haff.petclinic.security.UserDetailsAdapter;

@Service
@Slf4j
@RequiredArgsConstructor
public class RegisterService {
    private final OwnerRepository ownerRepository;
    private final Converter<OwnerForm, Owner> registrationFormOwner;

    public Owner registerOwner(OwnerForm ownerForm) {
        var owner = registrationFormOwner.convert(ownerForm);
        owner.getPersonalData().getUser().setRole(Role.OWNER);
        return ownerRepository.save(owner);
    }

    public void login(Owner owner) {
        var user = new UserDetailsAdapter(owner.getPersonalData().getUser());
        var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        log.info("Logged in as " + user.getUsername());
    }
}
