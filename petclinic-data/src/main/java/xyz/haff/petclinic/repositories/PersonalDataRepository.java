package xyz.haff.petclinic.repositories;

import org.springframework.data.repository.CrudRepository;
import xyz.haff.petclinic.models.PersonalData;

import java.util.UUID;

public interface PersonalDataRepository extends CrudRepository<PersonalData, UUID> {

    PersonalData findByUserId(UUID userId);
}
