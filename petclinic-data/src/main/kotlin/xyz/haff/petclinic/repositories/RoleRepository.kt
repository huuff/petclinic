package xyz.haff.petclinic.repositories

import org.springframework.data.r2dbc.repository.R2dbcRepository
import reactor.core.publisher.Mono
import xyz.haff.petclinic.models.Role
import java.util.*

interface RoleRepository : R2dbcRepository<Role, UUID> {

    fun findByName(name: String): Mono<Role>
}