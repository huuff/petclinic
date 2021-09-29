package xyz.haff.petclinic.repositories

import org.springframework.data.r2dbc.repository.Query
import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono
import xyz.haff.petclinic.models.Owner

@Repository
open class OwnerRepositoryCustomImpl(private val dbClient: DatabaseClient,
                                     private val userRepository: UserRepository): OwnerRepositoryCustom<Owner> {

    override fun <S : Owner?> save(owner: S): Mono<S> {
        return userRepository.save(owner!!.user).flatMap { (id) ->
            if (owner.version == 0) return@flatMap dbClient.sql(
                "INSERT INTO owner (id, version, user, first_name, last_name) " +
                        "VALUES (:id, :version, :user, :firstName, :lastName)"
            )
                .bind("id", owner.id)
                .bind("version", 1)
                .bind("user", id)
                .bind("firstName", owner.firstName)
                .bind("lastName", owner.lastName)
                .then()
                .then(Mono.just(owner))
            else return@flatMap Mono.error { RuntimeException() } // TODO
        }
    }
}