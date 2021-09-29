package xyz.haff.petclinic.repositories

import org.springframework.r2dbc.core.DatabaseClient
import reactor.core.publisher.Mono
import reactor.kotlin.core.util.function.component1
import reactor.kotlin.core.util.function.component2
import xyz.haff.petclinic.models.Vet

class VetCustomRepositoryImpl (
    private val dbClient: DatabaseClient,
    private val personalDataRepository: PersonalDataRepository
) : VetCustomRepository<Vet> {

    override fun <S : Vet> save(vet: S): Mono<S> {
        return personalDataRepository.save(vet.personalData)
            .map { savedPersonalData -> savedPersonalData.id }
            .flatMap{ personalDataId ->
            if (vet.version == 0)
                return@flatMap dbClient.sql(
                "INSERT INTO vet (id, version, personal_data) " +
                        "VALUES (:id, :version, :personal_data)"
            )
                .bind("id", vet.id)
                .bind("version", 1)
                .bind("personal_data", personalDataId)
                .then()
                .then(Mono.just(vet))
            else return@flatMap Mono.error { RuntimeException() } // TODO
        }
    }

}