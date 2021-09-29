package xyz.haff.petclinic.models

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Version
import java.util.*

data class Owner @JvmOverloads constructor(
    @Id public val id: UUID = UUID.randomUUID(),
    @Version public val version: Int = 0,
    public val user: User,
    public val firstName: String,
    public val lastName: String,
) {
    fun fullName(): String = "$firstName $lastName"
}
