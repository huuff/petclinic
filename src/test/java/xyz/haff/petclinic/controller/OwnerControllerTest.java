package xyz.haff.petclinic.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import xyz.haff.petclinic.models.Owner;
import xyz.haff.petclinic.repositories.OwnerRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class OwnerControllerTest {
    @Mock
    OwnerRepository ownerRepository;
    @InjectMocks
    OwnerController ownerController;
    MockMvc mockMvc;

    @Captor
    ArgumentCaptor<Owner> ownerCaptor;

    Set<Owner> testOwners;

    @BeforeEach
    public void setUp() {
        var owner1 = new Owner("Mike", "Test", mock(Set.class));
        var owner2 = new Owner("William", "Teston", mock(Set.class));

        testOwners = new HashSet<>() {{
            add(owner1);
            add(owner2);
        }};

        mockMvc = MockMvcBuilders.standaloneSetup(ownerController).build();
    }

    @Test
    void list() throws Exception {
        when(ownerRepository.findAll()).thenReturn(testOwners);

        mockMvc.perform(get("/owners/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/list"))
                .andExpect(model().attribute("owners", hasSize(2)))
                ;
    }

    @Test
    void initCreate() throws Exception {
        mockMvc.perform(get("/owners/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/edit"))
                .andExpect(model().attributeExists("owner"))
                ;

        verifyNoInteractions(ownerRepository);
    }

    @Test
    void createSavesWithNewId() throws Exception {
        var notExpected = "TEST_ID";

        mockMvc.perform(post("/owners/create")
            .param("id", notExpected));

        verify(ownerRepository).save(ownerCaptor.capture());

        assertNotEquals(notExpected, ownerCaptor.getValue());
    }

    @Test
    void initEdit() throws Exception {
        when(ownerRepository.findById(any())).thenReturn(testOwners.stream().findFirst());

        mockMvc.perform(get("/owners/1/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/edit"))
                .andExpect(model().attributeExists("owner"))
                ;
    }

    @Test
    void editSaveWithParamId() throws Exception {
        var ownerId = "1";

        mockMvc.perform(post("/owners/" + ownerId + "/edit"));
        verify(ownerRepository).save(ownerCaptor.capture());

        assertEquals(ownerId, ownerCaptor.getValue().getId());
    }

    @Test
    void processEdit() throws Exception {
        mockMvc.perform(post("/owners/1/edit"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/list"));
    }

    @Test
    void editEdits() throws Exception {
        var firstName = "FirstName";
        var lastName = "LastName";

        mockMvc.perform(post("/owners/1/edit")
            .param("firstName", firstName)
            .param("lastName", lastName))
                ;

        verify(ownerRepository).save(ownerCaptor.capture());

        assertEquals(firstName, ownerCaptor.getValue().getFirstName());
        assertEquals(lastName, ownerCaptor.getValue().getLastName());
    }
}
