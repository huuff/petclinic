package xyz.haff.petclinic.repositories

import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono
import xyz.haff.petclinic.models.Owner

@Repository
open class OwnerCustomRepositoryImpl(private val dbClient: DatabaseClient,
                                     private val personalDataRepository: PersonalDataRepository): OwnerCustomRepository<Owner> {

    @Transactional
    override fun <S : Owner> save(owner: S): Mono<S> {
        return personalDataRepository.save(owner.personalData)
            .map { savedPersonalData -> savedPersonalData.id }
            .flatMap { personalDataId ->
            if (owner.version == 0) return@flatMap dbClient.sql(
                "INSERT INTO owner (id, version, personal_data) " +
                        "VALUES (:id, :version, :personal_data)"
            )
                .bind("id", owner.id)
                .bind("version", 1)
                .bind("personal_data", personalDataId)
                .then()
                .then(Mono.just(owner))
            else return@flatMap Mono.error { RuntimeException() } // TODO
        }
    }
}