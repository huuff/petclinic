package xyz.haff.petclinic.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;
import xyz.haff.petclinic.models.PersonalData;
import xyz.haff.petclinic.models.Role;
import xyz.haff.petclinic.models.User;
import xyz.haff.petclinic.repositories.PersonalDataRepository;
import xyz.haff.petclinic.repositories.UserRepository;
import xyz.haff.petclinic.security.UserDetailsAdapter;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockUser;
import static xyz.haff.petclinic.testing.TestData.TEST_OWNER;

@ExtendWith(SpringExtension.class)
@WebFluxTest(IndexController.class)
public class IndexControllerTest {
    private static final String TEST_USERNAME = TEST_OWNER.getPersonalData().getUser().getUsername();

    @MockBean
    PersonalDataRepository personalDataRepository;

    @MockBean
    UserRepository userRepository;

    @Autowired
    WebTestClient client;

    @Test
    void nameAppearsInWelcome() {
        when(personalDataRepository.findByUsername(eq(TEST_USERNAME))).thenReturn(Mono.just(TEST_OWNER.getPersonalData()));
        when(userRepository.findByUsername(TEST_USERNAME)).thenReturn(Mono.just(TEST_OWNER.getPersonalData().getUser()));

        var htmlResult = client
                .mutateWith(mockUser(new UserDetailsAdapter(TEST_OWNER.getPersonalData().getUser())))
                .get()
                .uri("/")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .returnResult()
                .getResponseBody()
                ;

        assertThat(htmlResult).contains("Welcome, " + TEST_OWNER.getPersonalData().getFirstName());
    }
}
