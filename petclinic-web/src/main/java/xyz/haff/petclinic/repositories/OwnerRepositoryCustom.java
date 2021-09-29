package xyz.haff.petclinic.repositories;

import reactor.core.publisher.Mono;

public interface OwnerRepositoryCustom<T> {

    <S extends T> Mono<S> save(S owner);
}
