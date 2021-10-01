package xyz.haff.petclinic;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import xyz.haff.petclinic.models.Person;
import xyz.haff.petclinic.security.UserDetailsAdapter;

@Service
@Slf4j
@RequiredArgsConstructor
public class LoginService {
    private final AuthenticationManager authenticationManager;

    public void loginPerson(Person person) {
        var user = new UserDetailsAdapter(person.getPersonalData().getUser());
        var loginToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.passwordWithoutScheme(),  user.getAuthorities());
        loginToken.setDetails(user);
        var authenticatedUser = authenticationManager.authenticate(loginToken);
        SecurityContextHolder.getContext().setAuthentication(authenticatedUser);

        log.info("Logged in as " + user.getUsername());
    }
}
