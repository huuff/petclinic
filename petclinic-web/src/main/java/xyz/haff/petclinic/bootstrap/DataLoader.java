package xyz.haff.petclinic.bootstrap;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import xyz.haff.petclinic.models.Owner;
import xyz.haff.petclinic.models.User;
import xyz.haff.petclinic.repositories.OwnerRepository;

@Component
@RequiredArgsConstructor
@Slf4j
@Profile("demo")
public class DataLoader implements CommandLineRunner {
    private final OwnerRepository ownerRepository;

    @Override
    public void run(String... args) {
        var joeUser = new User("joe", "{noop}smith");
        var mikeUser = new User("mike", "{noop}weston");

        var joeSmith = new Owner(joeUser, "Joe", "Smith");
        ownerRepository.save(joeSmith).block();
        var michaelWeston = new Owner(mikeUser, "Michael", "Weston");
        ownerRepository.save(michaelWeston).block();

        ownerRepository.findAll()
                .doOnNext(owner -> log.info(owner.toString()))
                .subscribe();
    }
}
