package xyz.haff.petclinic.repositories

import org.springframework.r2dbc.core.DatabaseClient
import reactor.core.publisher.Mono
import xyz.haff.petclinic.models.PersonalData
import xyz.haff.petclinic.models.Vet
import java.util.*

class VetCustomRepositoryImpl (
    private val dbClient: DatabaseClient,
    private val personalDataRepository: PersonalDataRepository
) : VetCustomRepository<Vet> {

    override fun <S : Vet> save(vet: S): Mono<S> {
        return personalDataRepository.save(vet.personalData)
            .flatMap{ personalData -> when (vet.version) {
                0 -> insert(vet, personalData)
                else -> Mono.error(RuntimeException()) // TODO
            }
        }
    }

    private fun <S : Vet> insert(vet: S, personalData: PersonalData): Mono<S> {
       return dbClient.sql(
            "INSERT INTO vet (id, version, personal_data) " +
                    "VALUES (:id, :version, :personal_data)"
        )
            .bind("id", vet.id)
            .bind("version", 1)
            .bind("personal_data", personalData.id)
            .then()
            .then(Mono.just(vet.copy(
                personalData = personalData,
                version = 1
            ) as S))
    }

}