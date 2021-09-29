package xyz.haff.petclinic.repositories

import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.r2dbc.repository.R2dbcRepository
import reactor.core.publisher.Flux
import xyz.haff.petclinic.models.Owner
import java.util.*

interface OwnerRepository : R2dbcRepository<Owner, UUID>, OwnerCustomRepository<Owner> {

    @Query("""
        SELECT 
            o.id as OWNER_ID, 
            o.version as OWNER_VERSION,
            pd.id as PD_ID,
            pd.version as PD_VERSION,
            pd.first_name as PD_FIRST_NAME,
            pd.last_name as PD_LAST_NAME,
            u.id as U_ID,
            u.version as U_VERSION, 
            u.username as U_USERNAME,
            u.password as U_PASSWORD
            FROM owner as o 
            JOIN user as u ON pd.user=u.id
            JOIN personal_data as pd on o.personal_data=pd.id
    """)
    override fun findAll(): Flux<Owner>
}