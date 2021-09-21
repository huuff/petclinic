package xyz.haff.petclinic.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.CrudRepository;
import xyz.haff.petclinic.models.Owner;

public interface OwnerRepository extends ReactiveMongoRepository<Owner, String> {

    boolean existsByFirstNameAndLastName(String firstName, String lastName);
}
