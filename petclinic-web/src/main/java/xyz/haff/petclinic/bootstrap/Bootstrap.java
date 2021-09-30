package xyz.haff.petclinic.bootstrap;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import xyz.haff.petclinic.repositories.OwnerRepository;

@Component
@RequiredArgsConstructor
@Profile("demo")
public class Bootstrap implements CommandLineRunner {
    private final OwnerRepository ownerRepository;

    @Override
    public void run(String... args) throws Exception {
        ownerRepository.save(TestData.joeSmith);
        ownerRepository.save(TestData.michaelWeston);
    }
}
