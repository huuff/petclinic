package xyz.haff.petclinic.bootstrap;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import xyz.haff.petclinic.models.*;
import xyz.haff.petclinic.repositories.OwnerRepository;
import xyz.haff.petclinic.repositories.VetRepository;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
@Component
@Profile("demo")
public class DataLoader implements CommandLineRunner {
    private final OwnerRepository ownerRepository;
    private final VetRepository vetRepository;

    @Override
    public void run(String... args) throws Exception {
        Set<Pet> joesPets = new HashSet<>();
        var joe = new Owner("Joe", "Smith", joesPets);
        var mittens = new Pet("Mittens", PetType.CAT, LocalDate.of(2015, 3, 17));
        var toby = new Pet("Toby", PetType.DOG, LocalDate.of(2017, 8, 21));
        joesPets.add(mittens);
        joesPets.add(toby);

        var drKenny = new Vet("Kenny", "Wiggins", Specialty.OPHTHALMOLOGY);
        vetRepository.save(drKenny).block();
        var drBrandon = new Vet("Brandon", "Surimi", Specialty.SURGERY);
        vetRepository.save(drBrandon).block();

        var mittensVisit1 = new Visit(drKenny, LocalDate.of(2021, 1, 5), "Sneezy kitty");
        mittens.getVisits().add(mittensVisit1);
        var mittensVisit2 = new Visit(drKenny, LocalDate.of(2021, 8, 13), "Fluffy kitty");
        mittens.getVisits().add(mittensVisit2);

        var tobyVisit1 = new Visit(drKenny, LocalDate.of(2021, 3, 21), "Barking too much");
        toby.getVisits().add(tobyVisit1);
        var tobyVisit2 = new Visit(drBrandon, LocalDate.of(2021, 9, 15), "Oozing flims");
        toby.getVisits().add(tobyVisit2);
        ownerRepository.save(joe).block();
    }
}
