package xyz.haff.petclinic.repositories

import reactor.core.publisher.Mono

interface OwnerRepositoryCustom<T> {

    fun <S : T?> save(owner: S): Mono<S>
}