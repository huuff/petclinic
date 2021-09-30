package xyz.haff.petclinic.bootstrap;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.haff.petclinic.models.*;
import xyz.haff.petclinic.repositories.OwnerRepository;
import xyz.haff.petclinic.repositories.RoleRepository;
import xyz.haff.petclinic.repositories.VetRepository;

@Slf4j
@RequiredArgsConstructor
@Service
public class DataLoader  {
    private final OwnerRepository ownerRepository;
    private final VetRepository vetRepository;
    private final RoleRepository roleRepository;

    public final Owner TEST_OWNER = new Owner(
            new PersonalData(
                    "Testname",
                    "Testsurname",
                    new User(
                            new Role("OWNER"),
                            "testuser",
                            "testpassword")
            )
    );

    public void load() {
        // TODO: These should be somewhere else that always get inserted since they are valid for both demo, test and production
        roleRepository.save(new Role("OWNER")).block();
        roleRepository.save(new Role("VET")).block();

        var joeUser = new User(roleRepository.findByName("OWNER").block(), "joe", "{noop}smith");
        var joePerson = new PersonalData("Joe", "Smith", joeUser);
        var joeSmith = new Owner(joePerson);
        var savedJoe = ownerRepository.save(joeSmith).block();
        log.info("Saved " + savedJoe);

        var mikeUser = new User(roleRepository.findByName("OWNER").block(), "mike", "{noop}weston");
        var mikePerson = new PersonalData("Michael", "Weston", mikeUser);
        var mikeWeston = new Owner(mikePerson);
        var savedMike = ownerRepository.save(mikeWeston).block();
        log.info("Saved " + savedMike);

        var kennyUser = new User(roleRepository.findByName("VET").block(), "kenny", "{noop}wiggins");
        var kennyPerson = new PersonalData("Kenny", "Wiggins", kennyUser);
        var kennyWiggins = new Vet(kennyPerson);
        var savedKenny = vetRepository.save(kennyWiggins).block();
        log.info(("Saved " + savedKenny));
    }
}
