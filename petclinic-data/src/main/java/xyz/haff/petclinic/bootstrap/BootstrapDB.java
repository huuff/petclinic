package xyz.haff.petclinic.bootstrap;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import xyz.haff.petclinic.models.Owner;
import xyz.haff.petclinic.models.PersonalData;
import xyz.haff.petclinic.models.User;
import xyz.haff.petclinic.models.Vet;
import xyz.haff.petclinic.repositories.OwnerRepository;
import xyz.haff.petclinic.repositories.VetRepository;

@Component
@RequiredArgsConstructor
@Slf4j
@Profile("demo")
public class BootstrapDB implements CommandLineRunner {
    private final DataLoader dataLoader;

    @Override
    public void run(String... args) {
        dataLoader.load();
    }
}
