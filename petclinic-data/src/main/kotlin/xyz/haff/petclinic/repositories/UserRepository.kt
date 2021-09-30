package xyz.haff.petclinic.repositories

import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono
import xyz.haff.petclinic.entity_converters.UserReadConverter
import xyz.haff.petclinic.models.Role
import xyz.haff.petclinic.models.User
import java.util.*

@Repository
open class UserRepository(
    private val dbClient: DatabaseClient,
    private val roleRepository: RoleRepository,
    private val userReadConverter: UserReadConverter
) {

    open fun findByUsername(username: String): Mono<User> {
       return dbClient.sql("""
        SELECT
            u.id as U_ID,
            u.version as U_VERSION,
            u.username as U_USERNAME,
            u.password as U_PASSWORD,
            r.id as R_ID,
            r.version as R_VERSION,
            r.name as R_NAME
        FROM user as u 
        JOIN role as r on u.role_id = r.id
        WHERE u.username = :username
    """)
           .bind("username", username)
           .map { row -> userReadConverter.convert(row) }.first()
    }

    open fun save(user: User): Mono<User> {
        return roleRepository.save(user.role)
            .flatMap { role ->
                when (user.version) {
                    0 -> insert(user, role)
                    else -> Mono.error(RuntimeException()) // TODO
                }
            }
    }

    private fun  insert(user: User, role: Role): Mono<User> {
        return dbClient.sql(
            """
                    INSERT INTO USER (ID, VERSION, ROLE_ID, USERNAME, PASSWORD)
                    VALUES (:id, :version, :roleId, :username, :password);
                """.trimIndent()
        )
            .bind("id", user.id)
            .bind("version", 1)
            .bind("roleId", role.id)
            .bind("username", user.username)
            .bind("password", user.password)
            .then()
            .then(Mono.just(user.copy(
                role = role,
                version = 1
            )))
    }
}