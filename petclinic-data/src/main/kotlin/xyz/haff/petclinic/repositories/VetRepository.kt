package xyz.haff.petclinic.repositories

import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.r2dbc.repository.R2dbcRepository
import reactor.core.publisher.Flux
import xyz.haff.petclinic.models.Owner
import xyz.haff.petclinic.models.Vet
import java.util.*

interface VetRepository : R2dbcRepository<Vet, UUID>, VetCustomRepository<Vet> {

    @Query("""
        SELECT 
            v.id as VET_ID, 
            v.version as VET_VERSION,
            pd.id as PD_ID,
            pd.version as PD_VERSION,
            pd.first_name as PD_FIRST_NAME,
            pd.last_name as PD_LAST_NAME,
            u.id as U_ID,
            u.version as U_VERSION, 
            u.username as U_USERNAME,
            u.password as U_PASSWORD,
            r.id as R_ID,
            r.version as R_VERSION,
            r.name as R_NAME
            FROM vet as v 
            JOIN user as u ON pd.user=u.id
            JOIN personal_data as pd on v.personal_data=pd.id
            JOIN role as r on r.id=u.role_id
    """)
    override fun findAll(): Flux<Vet>
}