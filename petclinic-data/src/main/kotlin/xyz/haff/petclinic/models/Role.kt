package xyz.haff.petclinic.models

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Version
import org.springframework.security.core.GrantedAuthority
import java.util.*

data class Role @JvmOverloads constructor(
    @Id val id: UUID = UUID.randomUUID(),
    @Version val version: Int = 0,
    val name: String
): GrantedAuthority {
    override fun getAuthority(): String = name
}
