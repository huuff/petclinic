package xyz.haff.petclinic.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import xyz.haff.petclinic.models.Pet;

public interface PetRepository extends ReactiveMongoRepository<Pet, String> {
}
