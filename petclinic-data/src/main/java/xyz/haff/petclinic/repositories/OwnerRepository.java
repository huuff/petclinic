package xyz.haff.petclinic.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.CrudRepository;
import reactor.core.publisher.Mono;
import xyz.haff.petclinic.models.Owner;

public interface OwnerRepository extends ReactiveMongoRepository<Owner, String> {

    Mono<Boolean> existsByFirstNameAndLastName(String firstName, String lastName);
}
