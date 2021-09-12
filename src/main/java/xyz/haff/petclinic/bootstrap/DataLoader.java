package xyz.haff.petclinic.bootstrap;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import xyz.haff.petclinic.models.Pet;
import xyz.haff.petclinic.models.PetType;
import xyz.haff.petclinic.repositories.PetRepository;
import xyz.haff.petclinic.repositories.PetTypeRepository;

// TODO: Use this only in a test environment

@RequiredArgsConstructor
@Component
public class DataLoader implements CommandLineRunner {
    private final PetTypeRepository petTypeRepository;
    private final PetRepository petRepository;

    @Override
    public void run(String... args) throws Exception {
        var dog = new PetType("Dog");
        petTypeRepository.save(dog);
        var cat = new PetType("Cat");
        petTypeRepository.save(cat);

        var mittens = new Pet("Mittens", cat);
        petRepository.save(mittens);
        var toby = new Pet("Toby", dog);
        petRepository.save(toby);
    }
}
