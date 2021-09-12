package xyz.haff.petclinic.bootstrap;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import xyz.haff.petclinic.models.PetType;
import xyz.haff.petclinic.repositories.PetTypeRepository;

// TODO: Use this only in a test environment

@RequiredArgsConstructor
@Component
public class DataLoader implements CommandLineRunner {
    private final PetTypeRepository petTypeRepository;

    @Override
    public void run(String... args) throws Exception {
        var dog = new PetType("Dog");
        var cat = new PetType("Cat");

        petTypeRepository.save(dog);
        petTypeRepository.save(cat);
    }
}
