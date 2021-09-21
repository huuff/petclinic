package xyz.haff.petclinic.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.CrudRepository;
import reactor.core.publisher.Flux;
import xyz.haff.petclinic.models.Visit;

import java.util.Set;

public interface VisitRepository extends ReactiveMongoRepository<Visit, String> {

    Flux<Visit> findAllByPetId(String petId);
    Flux<Visit> findAllByVetId(String vetId);
}
