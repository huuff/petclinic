package xyz.haff.petclinic.repositories

import org.springframework.data.r2dbc.repository.R2dbcRepository
import xyz.haff.petclinic.models.PersonalData
import java.util.*

interface PersonalDataRepository : R2dbcRepository<PersonalData, UUID?>, PersonalDataCustomRepository<PersonalData>