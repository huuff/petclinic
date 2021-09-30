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
import xyz.haff.petclinic.bootstrap.DataLoader;
import xyz.haff.petclinic.entity_converters.*;
import xyz.haff.petclinic.models.Owner;
import xyz.haff.petclinic.models.PersonalData;
import xyz.haff.petclinic.models.User;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static xyz.haff.petclinic.testing.TestData.TEST_OWNER;

@ExtendWith(SpringExtension.class)
@Slf4j
@ActiveProfiles("test")
@DataR2dbcTest
@Import({
        ConvertersConfiguration.class,
        OwnerReadConverter.class,
        PersonalDataReadConverter.class,
        UserReadConverter.class,
        RoleReadConverter.class,
        VetReadConverter.class,
        RoleRepository.class,
        DataLoader.class
})
class OwnerRepositoryTest {

    @Autowired
    private OwnerRepository ownerRepository;

    // TODO: Test with some find by ID

    @Test
    void saveAndRead() {
        var savedOwner = ownerRepository.save(TEST_OWNER).block();

        assertThat(savedOwner).isNotNull();
        StepVerifier.create(ownerRepository.findAll())
                .expectNext(savedOwner)
                .verifyComplete()
                ;
    }
}