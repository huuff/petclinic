package xyz.haff.petclinic.bootstrap;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.haff.petclinic.models.Owner;
import xyz.haff.petclinic.models.PersonalData;
import xyz.haff.petclinic.models.User;
import xyz.haff.petclinic.models.Vet;
import xyz.haff.petclinic.repositories.OwnerRepository;
import xyz.haff.petclinic.repositories.VetRepository;

@RequiredArgsConstructor
@Slf4j
@Service
public class DataLoader  {
    private final OwnerRepository ownerRepository;
    private final VetRepository vetRepository;

    public final Owner TEST_OWNER = new Owner(
            new PersonalData(
                    "Testname",
                    "Testsurname",
                    new User(
                            "testuser",
                            "testpassword")
            )
    );

    public void load() {
        var joeUser = new User("joe", "{noop}smith");
        var joePerson = new PersonalData("Joe", "Smith", joeUser);
        var joeSmith = new Owner(joePerson);
        var savedJoe = ownerRepository.save(joeSmith).block();
        log.info("Saved " + savedJoe);

        var mikeUser = new User("mike", "{noop}weston");
        var mikePerson = new PersonalData("Michael", "Weston", mikeUser);
        var mikeWeston = new Owner(mikePerson);
        var savedMike = ownerRepository.save(mikeWeston).block();
        log.info("Saved " + savedMike);

        var kennyUser = new User("kenny", "{noop}wiggins");
        var kennyPerson = new PersonalData("Kenny", "Wiggins", kennyUser);
        var kennyWiggins = new Vet(kennyPerson);
        var savedKenny = vetRepository.save(kennyWiggins).block();
        log.info(("Saved " + savedKenny));
    }
}
