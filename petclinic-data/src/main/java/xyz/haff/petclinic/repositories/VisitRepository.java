package xyz.haff.petclinic.repositories;

import org.springframework.data.repository.CrudRepository;
import xyz.haff.petclinic.models.Visit;

import java.util.List;
import java.util.UUID;

public interface VisitRepository extends CrudRepository<Visit, UUID> {

    List<Visit> findByPet_OwnerId(UUID ownerId);
}
