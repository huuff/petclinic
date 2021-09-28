package xyz.haff.petclinic.bootstrap;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import xyz.haff.petclinic.models.Owner;
import xyz.haff.petclinic.repositories.OwnerRepository;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataLoader implements CommandLineRunner {
    private final OwnerRepository ownerRepository;

    @Override
    public void run(String... args) throws Exception {
        var joeSmith = new Owner("Joe", "Smith");
        ownerRepository.save(joeSmith).block();
        var michaelWeston = new Owner("Michael", "Weston");
        ownerRepository.save(michaelWeston).block();

        ownerRepository.findAll()
                .doOnNext(owner -> log.info(owner.toString()))
                .subscribe();
    }
}
