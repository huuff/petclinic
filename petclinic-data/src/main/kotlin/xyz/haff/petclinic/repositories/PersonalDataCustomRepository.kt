package xyz.haff.petclinic.repositories

import reactor.core.publisher.Mono

interface PersonalDataCustomRepository<T> {

    fun <S : T> save(personalData: S): Mono<S>
}