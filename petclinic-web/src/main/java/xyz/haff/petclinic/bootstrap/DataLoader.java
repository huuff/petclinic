package xyz.haff.petclinic.bootstrap;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import xyz.haff.petclinic.models.Owner;
import xyz.haff.petclinic.models.User;
import xyz.haff.petclinic.repositories.OwnerRepository;
import xyz.haff.petclinic.repositories.UserRepository;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataLoader implements CommandLineRunner {
    private final OwnerRepository ownerRepository;
    private final UserRepository userRepository;

    @Override
    public void run(String... args) {
        var joeUser = new User("joe", "smith");
        userRepository.save(joeUser);
        var mikeUser = new User("mike", "weston");
        userRepository.save(mikeUser);

        var joeSmith = new Owner(joeUser, "Joe", "Smith");
        ownerRepository.save(Mono.just(joeSmith)).block();
        var michaelWeston = new Owner(mikeUser, "Michael", "Weston");
        ownerRepository.save(Mono.just(michaelWeston)).block();

        ownerRepository.findAll()
                .doOnNext(owner -> log.info(owner.toString()))
                .subscribe();
    }
}
