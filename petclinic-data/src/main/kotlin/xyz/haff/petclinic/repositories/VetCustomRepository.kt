package xyz.haff.petclinic.repositories

import reactor.core.publisher.Mono

interface VetCustomRepository<T> {

    fun <S : T> save(vet : S): Mono<S>
}