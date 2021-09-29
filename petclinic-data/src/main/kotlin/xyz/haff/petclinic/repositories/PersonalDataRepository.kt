package xyz.haff.petclinic.repositories

import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.r2dbc.repository.R2dbcRepository
import reactor.core.publisher.Mono
import xyz.haff.petclinic.models.PersonalData
import java.util.*

interface PersonalDataRepository : R2dbcRepository<PersonalData, UUID?>, PersonalDataCustomRepository<PersonalData> {

    @Query("""
        SELECT
            pd.id as PD_ID,
            pd.version as PD_VERSION,
            pd.first_name as PD_FIRST_NAME,
            pd.last_name as PD_LAST_NAME,
            u.id as U_ID,
            u.version as U_VERSION,
            u.username as U_USERNAME,
            u.password as U_PASSWORD
        FROM personal_data pd
        JOIN user u on pd.user=u.id
        WHERE u.username=?1
    """)
    fun findByUsername(username: String): Mono<PersonalData>
}