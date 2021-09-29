package xyz.haff.petclinic.repositories;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import xyz.haff.petclinic.models.Owner;
import xyz.haff.petclinic.models.User;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Slf4j
@ActiveProfiles("test")
class OwnerRepositoryTest {

    @Autowired
    private OwnerRepository ownerRepository;

    @Test
    void saveAndRead() {
        var joeUser = new User("joe", "smith");
        var mikeUser = new User("mike", "weston");

        var joeSmith = new Owner(joeUser, "Joe", "Smith");
        ownerRepository.save(joeSmith).block();
        var michaelWeston = new Owner(mikeUser, "Michael", "Weston");
        ownerRepository.save(michaelWeston).block();

        ownerRepository.findAll().doOnNext(owner -> log.info(owner.toString())).subscribe();

        StepVerifier.create(ownerRepository.findAll())
                .expectNextCount(2)
                .verifyComplete()
                ;
    }
}