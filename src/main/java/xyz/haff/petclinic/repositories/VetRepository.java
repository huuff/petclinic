package xyz.haff.petclinic.repositories;

import org.springframework.data.repository.CrudRepository;
import xyz.haff.petclinic.models.Vet;

public interface VetRepository extends CrudRepository<Vet, String> {

    boolean existsByFirstNameAndLastName(String firstName, String lastName);
}
