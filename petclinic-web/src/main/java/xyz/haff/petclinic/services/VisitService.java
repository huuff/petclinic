package xyz.haff.petclinic.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;
import xyz.haff.petclinic.models.Owner;
import xyz.haff.petclinic.models.Pet;
import xyz.haff.petclinic.models.Visit;
import xyz.haff.petclinic.repositories.OwnerRepository;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VisitService {
    private final OwnerRepository ownerRepository;

    public Flux<Tuple2<Pet, Visit>> visitsByVet(String vetId) {
        return ownerRepository.findAll()
                .flatMapIterable(Owner::getPets)
                .flatMapIterable(pet -> pet.getVisits().stream().map(visit -> Tuples.of(pet, visit)).collect(Collectors.toList()))
                .filter(petAndVisit -> petAndVisit.getT2().getVet().getId().equals(vetId));
    }
}
