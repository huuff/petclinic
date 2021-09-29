package xyz.haff.petclinic.controllers;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import xyz.haff.petclinic.models.Owner;
import xyz.haff.petclinic.models.PersonalData;
import xyz.haff.petclinic.models.User;
import xyz.haff.petclinic.repositories.OwnerRepository;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebFluxTest(OwnersController.class)
class OwnersControllerTest {
    private final String FAKE_USERNAME = "fake";

    @MockBean
    OwnerRepository ownerRepository;

    @Autowired
    WebTestClient client;

    @Test
    @WithMockUser(FAKE_USERNAME)
    void listAll() {
        when(ownerRepository.findAll()).thenReturn(Flux.just(new Owner(
                UUID.randomUUID(),
                1,
                new PersonalData("asd", "asdf", new User(FAKE_USERNAME, "asd"))
        )));

        var htmlResult = client.get()
                .uri("/owners")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .returnResult()
                ;

        assertThat(htmlResult.getResponseBody()).contains("<td>asd asdf</td>");
    }
}