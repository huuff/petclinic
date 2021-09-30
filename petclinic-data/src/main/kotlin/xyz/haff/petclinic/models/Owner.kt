package xyz.haff.petclinic.models

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Version
import java.util.*

data class Owner @JvmOverloads constructor(
    val baseEntity: BaseEntity = BaseEntity(),
    val personalData: PersonalData
): Entity by baseEntity {
}
