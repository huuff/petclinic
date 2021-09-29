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
import xyz.haff.petclinic.entity_converters.ConvertersConfiguration;
import xyz.haff.petclinic.entity_converters.OwnerReadConverter;
import xyz.haff.petclinic.entity_converters.PersonalDataReadConverter;
import xyz.haff.petclinic.entity_converters.VetReadConverter;
import xyz.haff.petclinic.models.Owner;
import xyz.haff.petclinic.models.PersonalData;
import xyz.haff.petclinic.models.User;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(SpringExtension.class)
@Slf4j
@ActiveProfiles("test")
@DataR2dbcTest
@Import({
        ConvertersConfiguration.class,
        OwnerReadConverter.class,
        PersonalDataReadConverter.class,
        VetReadConverter.class,
        DataLoader.class
})
class OwnerRepositoryTest {

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private DataLoader dataLoader;

    @Test
    void saveAndRead() {
        var savedOwner = ownerRepository.save(dataLoader.TEST_OWNER).block();

        assertThat(savedOwner).isNotNull();
        StepVerifier.create(ownerRepository.findAll())
                .expectNext(savedOwner)
                .verifyComplete()
                ;
    }
}