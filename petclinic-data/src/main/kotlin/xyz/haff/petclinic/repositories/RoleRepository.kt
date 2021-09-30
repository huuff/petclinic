package xyz.haff.petclinic.repositories

import lombok.RequiredArgsConstructor
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.data.r2dbc.query.Criteria.where
import org.springframework.data.r2dbc.repository.R2dbcRepository
import org.springframework.data.relational.core.query.Query
import org.springframework.data.relational.core.query.Query.query
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono
import xyz.haff.petclinic.models.Role
import java.util.*

@Repository
open class RoleRepository(
    private val dbTemplate: R2dbcEntityTemplate
) {

    fun findByName(name: String): Mono<Role> {
        return dbTemplate.select(query(where("name").`is`(name)), Role::class.java).single()
    }

    fun save(role: Role): Mono<Role> {
        return dbTemplate.insert(role);
    }
}