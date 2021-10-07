package xyz.haff.petclinic.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import xyz.haff.petclinic.models.Owner;
import xyz.haff.petclinic.models.Role;
import xyz.haff.petclinic.models.Vet;
import xyz.haff.petclinic.models.forms.OwnerForm;
import xyz.haff.petclinic.models.forms.VetForm;
import xyz.haff.petclinic.repositories.OwnerRepository;
import xyz.haff.petclinic.repositories.VetRepository;
import xyz.haff.petclinic.security.UserDetailsAdapter;

@Service
@Slf4j
@RequiredArgsConstructor
public class RegisterService {
    private final OwnerRepository ownerRepository;
    private final VetRepository vetRepository;
    private final Converter<OwnerForm, Owner> ownerFormToOwner;
    private final Converter<VetForm, Vet> vetFormToVet;

    // TODO: Maybe these methods should be in their respective services?
    public Owner registerOwner(OwnerForm ownerForm) {
        var owner = ownerFormToOwner.convert(ownerForm);
        owner.getPersonalData().getUser().setRole(Role.OWNER);
        return ownerRepository.save(owner);
    }

    public Vet registerVet(VetForm vetForm) {
        var vet = vetFormToVet.convert(vetForm);
        vet.getPersonalData().getUser().setRole(Role.VET);
        return vetRepository.save(vet);
    }

    public void login(Owner owner) {
        var user = new UserDetailsAdapter(owner.getPersonalData().getUser());
        var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        log.info("Logged in as " + user.getUsername());
    }
}
