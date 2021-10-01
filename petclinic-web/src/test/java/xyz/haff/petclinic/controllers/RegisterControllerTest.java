package xyz.haff.petclinic.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.hamcrest.MockitoHamcrest;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import xyz.haff.petclinic.LoginService;
import xyz.haff.petclinic.repositories.OwnerRepository;
import xyz.haff.petclinic.repositories.PersonalDataRepository;
import xyz.haff.petclinic.repositories.UserRepository;

import static java.util.function.Predicate.isEqual;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.hamcrest.MockitoHamcrest.argThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(RegisterController.class)
class RegisterControllerTest {
    @MockBean
    OwnerRepository ownerRepository;
    @MockBean
    PersonalDataRepository personalDataRepository;
    @MockBean
    UserRepository userRepository;
    @MockBean
    LoginService loginService;
    @Autowired
    MockMvc mockMvc;

    @Test
    void registrationFormIsShown() throws Exception {
        mockMvc.perform(get("/register"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("registrationForm"))
                .andExpect(view().name("owners/register"))
        ;
    }

    @Test
    void registrationSavesUser() throws Exception {
        final var username = "USERNAME";
        final var password = "PASSWORD";

        mockMvc.perform(post("/register")
                .with(csrf())
                .param("firstName", "FIRST_NAME")
                .param("lastName", "LAST_NAME")
                .param("username", username)
                .param("password", password)
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));

        verify(ownerRepository)
                .save(argThat(
                        hasProperty("personalData",
                                hasProperty("user",
                                        hasProperty("username", is(username))
                                )
                        )));
    }
}