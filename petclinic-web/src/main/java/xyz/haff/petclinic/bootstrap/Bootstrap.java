package xyz.haff.petclinic.bootstrap;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import xyz.haff.petclinic.data.TestData;
import xyz.haff.petclinic.repositories.OwnerRepository;
import xyz.haff.petclinic.repositories.PetRepository;
import xyz.haff.petclinic.repositories.VetRepository;

// TODO: Log the saved entities here

@Component
@RequiredArgsConstructor
@Profile("demo")
public class Bootstrap implements CommandLineRunner {
    private final OwnerRepository ownerRepository;
    private final VetRepository vetRepository;
    private final PetRepository petRepository;

    @Override
    public void run(String... args) throws Exception {
        ownerRepository.save(TestData.joeSmith);
        ownerRepository.save(TestData.michaelWeston);
        vetRepository.save(TestData.kennyWiggins);
        vetRepository.save(TestData.williamGogler);
        petRepository.save(TestData.toby);
        petRepository.save(TestData.mittens);
    }
}
