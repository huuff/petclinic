package xyz.haff.petclinic.repositories;

import org.springframework.data.repository.CrudRepository;
import xyz.haff.petclinic.models.Owner;

public interface OwnerRepository extends CrudRepository<Owner, String> {

    boolean existsByFirstNameAndLastName(String firstName, String lastName);
}
