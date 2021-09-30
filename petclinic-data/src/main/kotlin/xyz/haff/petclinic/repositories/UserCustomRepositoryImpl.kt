package xyz.haff.petclinic.repositories

import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono
import xyz.haff.petclinic.models.Role
import xyz.haff.petclinic.models.User
import java.util.*

@Repository
open class UserCustomRepositoryImpl(
    private val dbClient: DatabaseClient,
    private val roleRepository: RoleRepository
) : UserCustomRepository<User> {

    override fun <S : User> save(user: S): Mono<S> {
        return roleRepository.save(user.role)
            .flatMap { role ->
                when (user.version) {
                    0 -> insert(user, role)
                    else -> Mono.error(RuntimeException()) // TODO
                }
            }
    }

    private fun <S : User> insert(user: S, role: Role): Mono<S> {
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
            ) as S))
    }
}