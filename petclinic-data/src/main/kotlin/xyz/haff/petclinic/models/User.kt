package xyz.haff.petclinic.models

data class User @JvmOverloads constructor(
    val baseEntity: BaseEntity = BaseEntity(),
    val role: Role,
    val username: String,
    val password: String
): Entity by baseEntity