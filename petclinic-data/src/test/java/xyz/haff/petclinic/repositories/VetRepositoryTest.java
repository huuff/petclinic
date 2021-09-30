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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static xyz.haff.petclinic.testing.TestData.TEST_VET;

@ExtendWith(SpringExtension.class)
@Slf4j
@ActiveProfiles("test")
@DataR2dbcTest
@Import({
        ConvertersConfiguration.class,
        PersonalDataReadConverter.class,
        UserReadConverter.class,
        RoleReadConverter.class,
        VetReadConverter.class,
        OwnerReadConverter.class,
        RoleRepository.class,
        DataLoader.class
})
public class VetRepositoryTest {
    @Autowired
    VetRepository vetRepository;

    @Test
    void saveAndRead() {
        var savedVet = vetRepository.save(TEST_VET).block();

        assertThat(savedVet).isNotNull();
        StepVerifier.create(vetRepository.findAll())
                .expectNext(savedVet)
                .verifyComplete()
                ;
    }

}
