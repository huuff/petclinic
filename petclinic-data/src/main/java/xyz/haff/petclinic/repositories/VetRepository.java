package xyz.haff.petclinic.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.CrudRepository;
import reactor.core.publisher.Mono;
import xyz.haff.petclinic.models.Vet;

public interface VetRepository extends ReactiveMongoRepository<Vet, String> {

    Mono<Boolean> existsByFirstNameAndLastName(String firstName, String lastName);
}
