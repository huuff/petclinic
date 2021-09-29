package xyz.haff.petclinic.repositories

import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.r2dbc.repository.R2dbcRepository
import reactor.core.publisher.Mono
import xyz.haff.petclinic.models.Vet
import java.util.*

interface VetRepository : R2dbcRepository<Vet, UUID>, VetCustomRepository<Vet> {

    @Query("""
        SELECT 
            v.id as V_ID, 
            v.version as V_VERSION,
            pd.id as PD_ID,
            pd.version as PD_VERSION,
            pd.first_name as PD_FIRST_NAME,
            pd.last_name as PD_LAST_NAME,
            u.id as U_ID,
            u.version as U_VERSION, 
            u.username as U_USERNAME,
            u.password as U_PASSWORD
            FROM vet as v
            JOIN user as u ON pd.user=u.id
            JOIN personal_data as pd on o.personal_data=pd.id
            WHERE username = ?1
    """)
    fun findByUsername(username: String): Mono<Vet>
}