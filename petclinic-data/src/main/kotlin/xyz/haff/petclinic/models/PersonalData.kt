package xyz.haff.petclinic.models

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Version
import java.util.*

// TODO: Or rather, just `Person`?

data class PersonalData @JvmOverloads constructor(
    val baseEntity: BaseEntity = BaseEntity(),
    val firstName: String,
    val lastName: String,
    val user: User
): Entity by baseEntity {
    fun fullName(): String = "$firstName $lastName"
}