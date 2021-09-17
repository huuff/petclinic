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
import org.springframework.validation.Validator;
import xyz.haff.petclinic.converters.PetFormToPetConverter;
import xyz.haff.petclinic.models.Owner;
import xyz.haff.petclinic.models.Pet;
import xyz.haff.petclinic.models.PetType;
import xyz.haff.petclinic.repositories.OwnerRepository;
import xyz.haff.petclinic.repositories.PetRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;
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
    @Mock
    PetRepository petRepository;
    PetFormToPetConverter petFormToPetConverter = new PetFormToPetConverter();

    OwnerController ownerController;
    MockMvc mockMvc;

    @Captor
    ArgumentCaptor<Owner> ownerCaptor;
    @Captor
    ArgumentCaptor<Pet> petCaptor;

    Set<Owner> testOwners;

    @BeforeEach
    public void setUp() {
        var owner1 = new Owner("Mike", "Test", new HashSet<>());
        var owner2 = new Owner("William", "Teston", new HashSet<>());

        testOwners = new HashSet<>() {{
            add(owner1);
            add(owner2);
        }};

        mockitoSession().initMocks(this);
        ownerController = new OwnerController(ownerRepository, petRepository, petFormToPetConverter);

        mockMvc = MockMvcBuilders
                .standaloneSetup(ownerController)
                .setValidator(mock(Validator.class))
                .build();
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

    @Test
    void initAddPet() throws Exception {
        when(ownerRepository.existsById(any())).thenReturn(true);

        mockMvc.perform(get("/owners/1/add_pet"))
                .andExpect(status().isOk())
                .andExpect(view().name("pets/edit"))
                .andExpect(model().attributeExists("pet"))
        ;
    }

    @Test
    void processAddPet() throws Exception {
        when(ownerRepository.findById(any())).thenReturn(testOwners.stream().findFirst());

        mockMvc.perform(post("/owners/1/add_pet"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/list"))
        ;
    }

    @Test
    void addPetAddsPet() throws Exception {
        var name = "PetName";
        var type = PetType.DOG;
        var birthDate = LocalDate.of(2012, 12, 12);
        var owner = testOwners.stream().findFirst().get();

        when(ownerRepository.findById(any())).thenReturn(Optional.of(owner));
        mockMvc.perform(post("/owners/1/add_pet")
                .param("name", name)
                .param("type", type.name())
                .param("birthDate", birthDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))));

        verify(petRepository).save(petCaptor.capture());

        assertEquals(name, petCaptor.getValue().getName());
        assertEquals(type, petCaptor.getValue().getType());
        assertEquals(birthDate, petCaptor.getValue().getBirthDate());
        assertEquals(owner, petCaptor.getValue().getOwner());
        assertThat(owner.getPets(), hasItems(petCaptor.getValue()));
    }
}
