package xyz.haff.petclinic.bootstrap;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import xyz.haff.petclinic.models.Owner;
import xyz.haff.petclinic.models.PersonalData;
import xyz.haff.petclinic.models.User;
import xyz.haff.petclinic.models.Vet;
import xyz.haff.petclinic.repositories.OwnerRepository;
import xyz.haff.petclinic.repositories.VetRepository;

@Component
@RequiredArgsConstructor
@Slf4j
@Profile("demo")
public class DataLoader implements CommandLineRunner {
    private final OwnerRepository ownerRepository;
    private final VetRepository vetRepository;

    @Override
    public void run(String... args) {
        var joeUser = new User("joe", "{noop}smith");
        var joePerson = new PersonalData("Joe", "Smith", joeUser);
        var joeSmith = new Owner(joePerson);
        ownerRepository.save(joeSmith).block();

        var mikeUser = new User("mike", "{noop}weston");
        var mikePerson = new PersonalData("Michael", "Weston", mikeUser);
        var mikeWeston = new Owner(mikePerson);
        ownerRepository.save(mikeWeston).block();

        var kennyUser = new User("kenny", "{noop}wiggins");
        var kennyPerson = new PersonalData("Kenny", "Wiggins", kennyUser);
        var kennyWiggins = new Vet(kennyPerson);
        vetRepository.save(kennyWiggins).block();

        ownerRepository.findAll()
                .doOnNext(owner -> log.info(owner.toString()))
                .subscribe();
    }
}
