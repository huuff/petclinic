package xyz.haff.petclinic.repositories

import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono
import xyz.haff.petclinic.entity_converters.PersonalDataReadConverter
import xyz.haff.petclinic.models.PersonalData
import xyz.haff.petclinic.models.User

@Repository
open class PersonalDataRepository(
    private val dbClient: DatabaseClient,
    private val userRepository: UserRepository,
    private val personalDataReadConverter: PersonalDataReadConverter
) {

    open fun findByUsername(username: String): Mono<PersonalData> {
        return dbClient.sql(
            """
        SELECT
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
        FROM personal_data pd
        JOIN user u on pd.user=u.id
        JOIN role r on u.role_id=r.id
        WHERE u.username=:username
    """
        )
            .bind("username", username)
            .map { row -> personalDataReadConverter.convert(row) }
            .one()
    }

    open fun save(personalData: PersonalData): Mono<PersonalData> {
        return userRepository.save(personalData.user)
            .flatMap { user ->
                when (personalData.version) {
                    0 -> insert(personalData, user)
                    else -> Mono.error(RuntimeException()) // TODO
                }
            }
    }

    private fun insert(personalData: PersonalData, user: User): Mono<PersonalData> {
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
            .then(
                Mono.just(
                    personalData.copy(
                        user = user,
                        version = 1
                    )
                )
            )
    }
}