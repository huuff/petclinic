package xyz.haff.petclinic.repositories;

import org.springframework.data.repository.CrudRepository;
import xyz.haff.petclinic.models.PetType;

public interface PetTypeRepository extends CrudRepository<PetType, String> {
}
