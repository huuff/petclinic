package xyz.haff.petclinic.repositories;

import org.springframework.data.repository.CrudRepository;
import xyz.haff.petclinic.models.Visit;

import java.util.Set;

public interface VisitRepository extends CrudRepository<Visit, String> {

    Set<Visit> findAllByPetId(String petId);
    Set<Visit> findAllByVetId(String vetId);
}
