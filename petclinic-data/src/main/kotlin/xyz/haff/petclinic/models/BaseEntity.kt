package xyz.haff.petclinic.models

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Version
import java.util.*

// TODO: Constructor from row with prefix?

data class BaseEntity(
    @Id override val id: UUID = UUID.randomUUID(),
    @Version override val version: Int = 0
): Entity {
    fun nextVersion(): BaseEntity = copy(version = version + 1) // TODO: this in Entity?
}
