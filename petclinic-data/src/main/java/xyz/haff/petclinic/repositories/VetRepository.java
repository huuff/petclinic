package xyz.haff.petclinic.repositories;

import org.springframework.data.repository.CrudRepository;
import xyz.haff.petclinic.models.Vet;

import java.util.UUID;

public interface VetRepository extends CrudRepository<Vet, UUID> {
}
