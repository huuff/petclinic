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
import xyz.haff.petclinic.models.PersonalData;
import xyz.haff.petclinic.models.Role;
import xyz.haff.petclinic.models.User;
import xyz.haff.petclinic.models.Vet;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

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
        DataLoader.class
})
public class VetRepositoryTest {
    private static final Vet TEST_VET = new Vet(
            new PersonalData(
                    "Testname",
                    "Testsurname",
                    new User(
                            new Role("VET"),
                            "testuser",
                            "testpassword")
            )
    );

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
