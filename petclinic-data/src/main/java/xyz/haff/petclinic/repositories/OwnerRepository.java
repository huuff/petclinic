package xyz.haff.petclinic.repositories;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import xyz.haff.petclinic.models.Owner;

public interface OwnerRepository extends ReactiveCrudRepository<Owner, String> {
}
