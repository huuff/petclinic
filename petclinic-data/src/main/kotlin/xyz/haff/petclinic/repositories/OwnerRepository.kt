package xyz.haff.petclinic.repositories

import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import xyz.haff.petclinic.entity_converters.OwnerReadConverter
import xyz.haff.petclinic.models.Owner
import xyz.haff.petclinic.models.PersonalData

@Repository
open class OwnerRepository(
    private val dbClient: DatabaseClient,
    private val personalDataRepository: PersonalDataRepository,
    private val ownerReadConverter: OwnerReadConverter
) {

    open fun findAll(): Flux<Owner> {
        return dbClient.sql("""
        SELECT 
            o.id as OWNER_ID, 
            o.version as OWNER_VERSION,
            pd.id as PD_ID,
            pd.version as PD_VERSION,
            pd.first_name as PD_FIRST_NAME,
            pd.last_name as PD_LAST_NAME,
            u.id as U_ID,
            u.version as U_VERSION, 
            u.username as U_USERNAME,
            u.password as U_PASSWORD,
            r.id as R_ID,
            r.version as R_VERSION,
            r.name as R_NAME
        FROM owner as o 
        JOIN user as u ON pd.user=u.id
        JOIN personal_data as pd on o.personal_data=pd.id
        JOIN role as r on r.id=u.role_id
    """)
            .map { row -> ownerReadConverter.convert(row)}
            .all()
    }

    open fun save(owner: Owner): Mono<Owner> {
        return personalDataRepository.save(owner.personalData)
            .flatMap { personalData ->
                when (owner.version) {
                    0 -> insert(owner, personalData)
                    else -> Mono.error { RuntimeException() } // TODO
                }
            }
    }

    private fun insert(owner: Owner, personalData: PersonalData): Mono<Owner> {
        return dbClient.sql(
            """
            INSERT INTO owner (id, version, personal_data)
            VALUES (:id, :version, :personal_data)
        """.trimIndent()
        )
            .bind("id", owner.id)
            .bind("version", 1)
            .bind("personal_data", personalData.id)
            .then()
            .then(
                Mono.just(
                    owner.copy(
                        personalData = personalData,
                        version = 1
                    )
                )
            )
    }
}