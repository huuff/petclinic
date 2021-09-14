package xyz.haff.petclinic.bootstrap;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import xyz.haff.petclinic.models.Owner;
import xyz.haff.petclinic.models.Pet;
import xyz.haff.petclinic.models.PetType;
import xyz.haff.petclinic.repositories.OwnerRepository;
import xyz.haff.petclinic.repositories.PetRepository;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

// TODO: Use this only in a test environment

@RequiredArgsConstructor
@Component
public class DataLoader implements CommandLineRunner {
    private final PetRepository petRepository;
    private final OwnerRepository ownerRepository;

    @Override
    public void run(String... args) throws Exception {
        Set<Pet> joesPets = new HashSet<>();
        var joe = new Owner("Joe", "Smith", joesPets);
        var mittens = new Pet("Mittens", PetType.CAT, LocalDate.of(2015, 3, 17), joe);
        var toby = new Pet("Toby", PetType.DOG, LocalDate.of(2017, 8, 21), joe);
        joesPets.add(mittens);
        joesPets.add(toby);

        ownerRepository.save(joe);
        petRepository.save(mittens);
        petRepository.save(toby);
    }
}
