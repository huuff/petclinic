package xyz.haff.petclinic.bootstrap;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import xyz.haff.petclinic.models.*;
import xyz.haff.petclinic.repositories.OwnerRepository;
import xyz.haff.petclinic.repositories.PetRepository;
import xyz.haff.petclinic.repositories.VetRepository;
import xyz.haff.petclinic.repositories.VisitRepository;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
@Component
@Profile("demo")
public class DataLoader implements CommandLineRunner {
    private final PetRepository petRepository;
    private final OwnerRepository ownerRepository;
    private final VetRepository vetRepository;
    private final VisitRepository visitRepository;

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

        var drKenny = new Vet("Kenny", "Wiggins", Specialty.OPHTHALMOLOGY);
        vetRepository.save(drKenny);
        var drBrandon = new Vet("Brandon", "Surimi", Specialty.SURGERY);
        vetRepository.save(drBrandon);

        var mittensVisit1 = new Visit(mittens, drKenny, LocalDate.of(2021, 1, 5), "Sneezy kitty");
        visitRepository.save(mittensVisit1);
        var mittensVisit2 = new Visit(mittens, drKenny, LocalDate.of(2021, 8, 13), "Fluffy kitty");
        visitRepository.save(mittensVisit2);

        var tobyVisit1 = new Visit(toby, drKenny, LocalDate.of(2021, 3, 21), "Barking too much");
        visitRepository.save(tobyVisit1);
        var tobyVisit2 = new Visit(toby, drBrandon, LocalDate.of(2021, 9, 15), "Oozing flims");
        visitRepository.save(tobyVisit2);
    }
}
