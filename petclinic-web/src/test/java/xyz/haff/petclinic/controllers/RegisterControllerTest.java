package xyz.haff.petclinic.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import xyz.haff.petclinic.services.OwnerService;
import xyz.haff.petclinic.services.RegisterService;
import xyz.haff.petclinic.repositories.UserRepository;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(RegisterController.class)
class RegisterControllerTest {
    @MockBean
    RegisterService registerService;
    @MockBean
    OwnerService ownerService;
    @MockBean
    UserRepository userRepository;
    @Autowired
    MockMvc mockMvc;

    @Test
    void registrationFormIsShown() throws Exception {
        mockMvc.perform(get(RegisterController.PATH))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("ownerForm"))
                .andExpect(view().name("owners/edit"))
        ;
    }

    // With this amount of mocking... is anything here tested at all?
    @Test
    void registrationSavesUser() throws Exception {
        when(ownerService.checkNewIsValid(any(), any())).thenReturn(true);

        mockMvc.perform(post(RegisterController.PATH)
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));

        verify(registerService).registerOwner(any());
        verify(registerService).login(any());
    }
}