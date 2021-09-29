package xyz.haff.petclinic.repositories

import reactor.core.publisher.Mono

// TODO: merge this with `OwnerCustomRepository` since they are exactly the same thing
interface VetCustomRepository<T> {

    fun <S : T> save(vet : S): Mono<S>
}