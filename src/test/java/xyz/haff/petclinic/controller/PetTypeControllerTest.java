package xyz.haff.petclinic.controller;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.ui.Model;
import xyz.haff.petclinic.models.PetType;
import xyz.haff.petclinic.repositories.PetTypeRepository;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

// TODO: Improve this (`RestTemplate`?)

@RequiredArgsConstructor
@ExtendWith(SpringExtension.class)
@SpringBootTest
class PetTypeControllerTest {

    @Autowired
    private PetTypeController controller;
    @Autowired
    private PetTypeRepository repository;

    @BeforeEach
    void setUp() {
        var cat = new PetType("Cat");
        var dog = new PetType("Dog");

        repository.save(cat);
        repository.save(dog);
    }

    @Test
    void returnsTypes() {
        var model = Mockito.mock(Model.class);

        controller.list(model);

        verify(model).addAttribute(eq("pet_types"), any());
    }
}