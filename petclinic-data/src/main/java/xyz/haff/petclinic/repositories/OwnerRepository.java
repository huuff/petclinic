package xyz.haff.petclinic.repositories;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import xyz.haff.petclinic.models.Owner;

import java.util.UUID;

public interface OwnerRepository extends R2dbcRepository<Owner, UUID>, OwnerRepositoryCustom<Owner> {

    // TODO: Use kotlin? or JDK17 strings?
    @Query("SELECT " +
            "o.id as OWNER_ID, " +
            "o.version as OWNER_VERSION, " +
            "o.first_name as FIRST_NAME, " +
            "o.last_name as LAST_NAME, " +
            "u.id as USER_ID, " +
            "u.version as USER_VERSION, " +
            "u.username as USERNAME, " +
            "u.password as PASSWORD " +
            "FROM owner as o JOIN user as u ON o.user=u.id")
    Flux<Owner> findAll();
}
