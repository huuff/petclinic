package xyz.haff.petclinic.bootstrap;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import xyz.haff.petclinic.data.TestData;
import xyz.haff.petclinic.repositories.OwnerRepository;
import xyz.haff.petclinic.repositories.PetRepository;
import xyz.haff.petclinic.repositories.VetRepository;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
@Profile("demo")
public class Bootstrap implements CommandLineRunner {
    private final OwnerRepository ownerRepository;
    private final VetRepository vetRepository;
    private final PetRepository petRepository;

    @Override
    public void run(String... args) {
        var savedJoe = ownerRepository.save(TestData.joeSmith);
        logSaved(savedJoe);

        var savedMichael = ownerRepository.save(TestData.michaelWeston);
        logSaved(savedMichael);

        var savedKenny = vetRepository.save(TestData.kennyWiggins);
        logSaved(savedKenny);

        var savedWill = vetRepository.save(TestData.williamGogler);
        logSaved(savedWill);

        var savedToby = petRepository.save(TestData.toby);
        logSaved(savedToby);

        var savedMittens = petRepository.save(TestData.mittens);
        logSaved(savedMittens);
    }

    private void logSaved(Object object) {
        log.info("Saved " + object);
    }
}
