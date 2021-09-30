package xyz.haff.petclinic.repositories

import reactor.core.publisher.Mono

interface UserCustomRepository<T> {

    fun <S : T> save(user: S): Mono<S>
}