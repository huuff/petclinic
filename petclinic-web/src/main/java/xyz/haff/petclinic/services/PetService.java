package xyz.haff.petclinic.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import xyz.haff.petclinic.exceptions.NotFoundException;
import xyz.haff.petclinic.models.Owner;
import xyz.haff.petclinic.models.Pet;
import xyz.haff.petclinic.repositories.OwnerRepository;

@Service
@RequiredArgsConstructor
public class PetService {

    private final OwnerRepository ownerRepository;

    public Mono<Pet> findPetByOwnerIdAndName(String ownerId, String petName) {
        return ownerRepository.findById(ownerId)
                .switchIfEmpty(Mono.error(NotFoundException::new))
                .flatMapIterable(Owner::getPets)
                .filter(pet -> pet.getName().equals(petName))
                .switchIfEmpty(Mono.error(NotFoundException::new))
                .next();
    }

    public Mono<Object> mergePet(String ownerId, String petName, Pet pet) {
        return findOwnerAndPetByOwnerIdAndPetName(ownerId, petName)
                .flatMap((ownerAndPet) -> {
                    ownerAndPet.getT2().setName(pet.getName());
                    ownerAndPet.getT2().setType(pet.getType());
                    ownerAndPet.getT2().setBirthDate(pet.getBirthDate());
                    return ownerRepository.save(ownerAndPet.getT1());
                });
    }

    private Mono<Tuple2<Owner, Pet>> findOwnerAndPetByOwnerIdAndPetName(String ownerId, String petName) {
        return ownerRepository.findById(ownerId)
                .switchIfEmpty(Mono.error(NotFoundException::new))
                .zipWhen((owner) ->
                        Flux.fromIterable(owner.getPets())
                                .filter((ownerPet) -> ownerPet.getName().equals(petName))
                                .switchIfEmpty(Mono.error(NotFoundException::new))
                                .next()
                );
    }
}
