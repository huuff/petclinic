package xyz.haff.petclinic.repositories

import reactor.core.publisher.Mono

// TODO: All my custom repositories are exactly the same but I don't seem to manage to fix it otherwise
interface UserCustomRepository<T> {

    fun <S : T> save(user: S): Mono<S>
}