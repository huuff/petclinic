package xyz.haff.petclinic.bootstrap;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import xyz.haff.petclinic.models.Pet;
import xyz.haff.petclinic.models.PetType;
import xyz.haff.petclinic.repositories.PetRepository;

// TODO: Use this only in a test environment

@RequiredArgsConstructor
@Component
public class DataLoader implements CommandLineRunner {
    private final PetRepository petRepository;

    @Override
    public void run(String... args) throws Exception {
        var mittens = new Pet("Mittens", PetType.CAT);
        petRepository.save(mittens);
        var toby = new Pet("Toby", PetType.DOG);
        petRepository.save(toby);
    }
}
