package xyz.haff.petclinic.repositories

import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono
import xyz.haff.petclinic.models.Owner
import xyz.haff.petclinic.models.PersonalData
import java.util.*

@Repository
open class OwnerCustomRepositoryImpl(private val dbClient: DatabaseClient,
                                     private val personalDataRepository: PersonalDataRepository): OwnerCustomRepository<Owner> {

    @Transactional
    override fun <S : Owner> save(owner: S): Mono<S> {
        return personalDataRepository.save(owner.personalData)
            .flatMap { personalData ->
                when (owner.version) {
                    0 -> insert(owner, personalData)
                    else -> Mono.error { RuntimeException() } // TODO
                }
        }
    }

    private fun <S : Owner> insert(owner: S, personalData: PersonalData): Mono<S> {
        return dbClient.sql("""
            INSERT INTO owner (id, version, personal_data)
            VALUES (:id, :version, :personal_data)
        """.trimIndent())
            .bind("id", owner.id)
            .bind("version", 1)
            .bind("personal_data", personalData.id)
            .then()
            .then(Mono.just(owner.copy(
                personalData = personalData,
                baseEntity = owner.baseEntity.nextVersion()
            ) as S))
    }
}