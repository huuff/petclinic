package xyz.haff.petclinic.repositories;

import org.springframework.data.repository.CrudRepository;
import xyz.haff.petclinic.models.Visit;

public interface VisitRepository extends CrudRepository<Visit, String> {
}
