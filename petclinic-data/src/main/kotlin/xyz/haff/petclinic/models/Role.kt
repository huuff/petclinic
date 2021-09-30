package xyz.haff.petclinic.models

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Version
import org.springframework.security.core.GrantedAuthority
import java.util.*

data class Role @JvmOverloads constructor(
    val baseEntity: BaseEntity = BaseEntity(),
    val name: String
): GrantedAuthority, Entity by baseEntity {
    override fun getAuthority(): String = name
}
