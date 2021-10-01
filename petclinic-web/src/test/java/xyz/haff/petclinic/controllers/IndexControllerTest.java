package xyz.haff.petclinic.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import xyz.haff.petclinic.models.PersonalData;
import xyz.haff.petclinic.repositories.PersonalDataRepository;
import xyz.haff.petclinic.repositories.UserRepository;
import xyz.haff.petclinic.security.UserDetailsAdapter;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static xyz.haff.petclinic.bootstrap.TestData.TEST_OWNER;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = IndexController.class)
class IndexControllerTest {
    private static final PersonalData TEST_PERSON = TEST_OWNER.getPersonalData();
    @MockBean
    PersonalDataRepository personalDataRepository;
    @MockBean
    UserRepository userRepository;
    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        when(personalDataRepository.findByUserId(TEST_PERSON.getUser().getId())).thenReturn(TEST_PERSON);
        when(userRepository.findByUsername(TEST_PERSON.getUser().getUsername())).thenReturn(TEST_PERSON.getUser());
    }

    @Test
    void nameAppearsOnIndex() throws Exception {
        mockMvc.perform(get("/").with(user(new UserDetailsAdapter(TEST_OWNER.getPersonalData().getUser()))))
                .andExpect(status().isOk())
                .andExpect(model().attribute("name", TEST_OWNER.getPersonalData().getFirstName()))
                .andExpect(view().name("index"))
                ;
    }

}