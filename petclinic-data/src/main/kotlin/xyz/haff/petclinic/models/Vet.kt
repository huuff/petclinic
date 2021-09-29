package xyz.haff.petclinic.models

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Version
import java.util.*

data class Vet @JvmOverloads constructor(
    @Id val id: UUID = UUID.randomUUID(),
    @Version val version: Int = 0,
    val personalData: PersonalData
)