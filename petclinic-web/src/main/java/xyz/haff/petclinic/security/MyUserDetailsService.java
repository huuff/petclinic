package xyz.haff.petclinic.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import xyz.haff.petclinic.repositories.UserRepository;

@RequiredArgsConstructor
@Service
public class MyUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .switchIfEmpty(Mono.error(() -> new RuntimeException("User " + username + " not found")))// TODO: Actual, own exception
                .map(MyUserPrincipal::new)
                .block(); // TODO: Probably want to not block
    }
}
