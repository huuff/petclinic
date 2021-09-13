package xyz.haff.petclinic.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import xyz.haff.petclinic.models.Owner;
import xyz.haff.petclinic.models.Pet;
import xyz.haff.petclinic.models.PetType;
import xyz.haff.petclinic.repositories.PetRepository;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(MockitoExtension.class)
public class PetControllerTest {

    @Mock
    PetRepository repository;

    @InjectMocks
    PetController controller;

    MockMvc mockMvc;
    Set<Pet> pets;

    @BeforeEach
    void setUp() {
        var mittens = new Pet("Mittens", PetType.CAT, mock(Owner.class));

        pets = new HashSet<>() {{ add(mittens); }};

        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();
    }

    @Test
    void list() throws Exception {
        when(repository.findAll()).thenReturn(pets);

        mockMvc.perform(get("/pets/list"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("pets"))
                .andExpect(view().name("pets/list"))
                ;
    }
}
