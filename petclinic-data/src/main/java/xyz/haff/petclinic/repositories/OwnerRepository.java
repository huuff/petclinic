package xyz.haff.petclinic.repositories;

import org.springframework.data.repository.CrudRepository;
import xyz.haff.petclinic.models.Owner;

import java.util.UUID;

public interface OwnerRepository extends CrudRepository<Owner, UUID> {
}
