package xyz.haff.petclinic.repositories

import org.springframework.r2dbc.core.DatabaseClient
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono
import xyz.haff.petclinic.models.User

@Repository
open class UserCustomRepositoryImpl(
    private val dbClient : DatabaseClient,
    private val roleRepository: RoleRepository
) : UserCustomRepository<User> {

    // TODO: Version not 0
    override fun <S : User> save(user: S): Mono<S> {
        return roleRepository.save(user.role)
            .map { savedRole -> savedRole.id }
            .flatMap { roleId ->
                return@flatMap dbClient.sql("""
                    INSERT INTO USER (ID, VERSION, ROLE_ID, USERNAME, PASSWORD)
                    VALUES (:id, :version, :roleId, :username, :password);
                """.trimIndent()
                )
                    .bind("id", user.id)
                    .bind("version", user.version)
                    .bind("roleId", roleId)
                    .bind("username", user.username)
                    .bind("password", user.password)
                    .then()
                    .then(Mono.just(user))
            }
    }
}