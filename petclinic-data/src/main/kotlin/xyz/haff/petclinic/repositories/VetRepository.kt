package xyz.haff.petclinic.repositories

import org.springframework.data.r2dbc.repository.R2dbcRepository
import xyz.haff.petclinic.models.Vet
import java.util.*

interface VetRepository : R2dbcRepository<Vet, UUID>, VetCustomRepository<Vet> {
}