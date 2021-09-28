package xyz.haff.petclinic.repositories;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import xyz.haff.petclinic.models.Owner;

public interface OwnerRepository {

    // TODO: Maybe accept Flux?
    Mono<Object> save(Mono<Owner> ownerPublisher);

    Flux<Owner> findAll();
}
