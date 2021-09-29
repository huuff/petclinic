package xyz.haff.petclinic.repositories;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.test.StepVerifier;
import xyz.haff.petclinic.entity_converters.ConvertersConfiguration;
import xyz.haff.petclinic.entity_converters.OwnerReadConverter;
import xyz.haff.petclinic.entity_converters.PersonalDataReadConverter;
import xyz.haff.petclinic.entity_converters.VetReadConverter;
import xyz.haff.petclinic.models.Owner;
import xyz.haff.petclinic.models.PersonalData;
import xyz.haff.petclinic.models.User;

@ExtendWith(SpringExtension.class)
@Slf4j
@ActiveProfiles("test")
@DataR2dbcTest
@Import({ ConvertersConfiguration.class, OwnerReadConverter.class, PersonalDataReadConverter.class, VetReadConverter.class })
class OwnerRepositoryTest {

    @Autowired
    private OwnerRepository ownerRepository;
    // TODO: Put the data somewhere else

    @Test
    void saveAndRead() {
        var joeUser = new User("joe", "{noop}smith");
        var joePerson = new PersonalData("Joe", "Smith", joeUser);
        var joeSmith = new Owner(joePerson);
        ownerRepository.save(joeSmith).block();

        var mikeUser = new User("mike", "{noop}weston");
        var mikePerson = new PersonalData("Michael", "Weston", mikeUser);
        var mikeWeston = new Owner(mikePerson);
        ownerRepository.save(mikeWeston).block();

        ownerRepository.findAll().doOnNext(owner -> log.info(owner.toString())).subscribe();

        StepVerifier.create(ownerRepository.findAll())
                .expectNextCount(2)
                .verifyComplete()
                ;
    }
}