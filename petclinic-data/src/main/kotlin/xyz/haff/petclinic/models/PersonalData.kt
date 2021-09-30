package xyz.haff.petclinic.models

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Version
import java.util.*

// TODO: Or rather, just `Person`?

data class PersonalData @JvmOverloads constructor(
    @Id val id: UUID = UUID.randomUUID(),
    @Version val version: Int = 0,
    val firstName: String,
    val lastName: String,
    val user: User
) {
    fun fullName(): String = "$firstName $lastName"
}