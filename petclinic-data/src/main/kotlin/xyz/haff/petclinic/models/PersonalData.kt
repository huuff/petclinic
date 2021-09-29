package xyz.haff.petclinic.models

import java.util.*

// TODO: Or rather, just `Person`?

data class PersonalData @JvmOverloads constructor(
    val id: UUID = UUID.randomUUID(),
    val version: Int = 0,
    val firstName: String,
    val lastName: String,
    val user: User
) {
    fun fullName(): String = "$firstName $lastName"
}