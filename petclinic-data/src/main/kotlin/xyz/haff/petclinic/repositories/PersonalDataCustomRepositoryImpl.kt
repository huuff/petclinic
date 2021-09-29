package xyz.haff.petclinic.repositories

import org.springframework.r2dbc.core.DatabaseClient
import reactor.core.publisher.Mono
import xyz.haff.petclinic.models.PersonalData

class PersonalDataCustomRepositoryImpl(
    private val userRepository: UserRepository,
    private val databaseClient: DatabaseClient
) : PersonalDataCustomRepository<PersonalData> {


    override fun <S : PersonalData> save(personalData: S): Mono<S> {
        return userRepository.save(personalData.user)
            .map { savedUser -> savedUser.id }
            .flatMap { userId ->
                databaseClient.sql("""
                    INSERT INTO PERSONAL_DATA (ID, VERSION, USER, FIRST_NAME, LAST_NAME)
                    VALUES (:id, :version, :userId, :firstName, :lastName)
                """.trimIndent())
                    .bind("id", personalData.id)
                    .bind("version", personalData.version)
                    .bind("userId", personalData.user.id)
                    .bind("firstName", personalData.firstName)
                    .bind("lastName", personalData.lastName)
                    .then()
                    .then(Mono.just(personalData))
            }
    }
}