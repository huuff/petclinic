package xyz.haff.petclinic.repositories;

import org.springframework.data.repository.CrudRepository;
import xyz.haff.petclinic.models.Pet;

import java.util.UUID;

public interface PetRepository extends CrudRepository<Pet, UUID> {
}
