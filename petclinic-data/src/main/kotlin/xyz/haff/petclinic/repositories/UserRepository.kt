package xyz.haff.petclinic.repositories

import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.r2dbc.repository.R2dbcRepository
import reactor.core.publisher.Mono
import xyz.haff.petclinic.models.User
import java.util.*

interface UserRepository : R2dbcRepository<User, UUID>, UserCustomRepository<User> {

    @Query("""
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
        WHERE u.username = ?1
    """)
    fun findByUsername(username: String): Mono<User>
}