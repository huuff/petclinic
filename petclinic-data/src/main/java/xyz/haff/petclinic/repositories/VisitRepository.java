package xyz.haff.petclinic.repositories;

import org.springframework.data.repository.CrudRepository;
import xyz.haff.petclinic.models.Visit;

import java.util.UUID;

public interface VisitRepository extends CrudRepository<Visit, UUID> {
}
