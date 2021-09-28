package xyz.haff.petclinic.repositories;

import org.springframework.data.r2dbc.convert.R2dbcConverter;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import xyz.haff.petclinic.models.Owner;

import java.util.UUID;

public interface OwnerRepository  extends R2dbcRepository<Owner, UUID>, OwnerRepositoryCustom<Owner> {
}
