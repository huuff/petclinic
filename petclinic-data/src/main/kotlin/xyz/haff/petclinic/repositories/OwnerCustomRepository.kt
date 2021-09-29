package xyz.haff.petclinic.repositories

import reactor.core.publisher.Mono

interface OwnerCustomRepository<T> {

    fun <S : T> save(owner: S): Mono<S>
}