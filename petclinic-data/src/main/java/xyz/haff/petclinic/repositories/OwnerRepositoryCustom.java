package xyz.haff.petclinic.repositories;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import xyz.haff.petclinic.models.Owner;

public interface OwnerRepositoryCustom<T> {

    <S extends T> Mono<S> save(S owner);

    Flux<Owner> findAll();
}
