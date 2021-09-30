package xyz.haff.petclinic.repositories

import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono
import xyz.haff.petclinic.models.PersonalData
import xyz.haff.petclinic.models.User
import java.util.*

@Repository
open class PersonalDataCustomRepositoryImpl(
    private val userRepository: UserRepository,
    private val dbClient: DatabaseClient
) : PersonalDataCustomRepository<PersonalData> {


    override fun <S : PersonalData> save(personalData: S): Mono<S> {
        return userRepository.save(personalData.user)
            .flatMap { user ->
                when (personalData.version) {
                    0 -> insert(personalData, user)
                    else -> Mono.error(RuntimeException()) // TODO
                }
            }
    }

    private fun <S : PersonalData> insert(personalData: S, user: User): Mono<S> {
        return dbClient.sql(
            """
                    INSERT INTO PERSONAL_DATA (ID, VERSION, USER, FIRST_NAME, LAST_NAME)
                    VALUES (:id, :version, :userId, :firstName, :lastName)
                """.trimIndent()
        )
            .bind("id", personalData.id)
            .bind("version", 1)
            .bind("userId", user.id)
            .bind("firstName", personalData.firstName)
            .bind("lastName", personalData.lastName)
            .then()
            .then(Mono.just(personalData.copy(
                user = user,
                baseEntity = personalData.baseEntity.nextVersion()
            ) as S))
    }
}