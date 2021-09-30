package xyz.haff.petclinic.models

import java.util.*

interface Entity {
    val id: UUID
    val version: Int
}