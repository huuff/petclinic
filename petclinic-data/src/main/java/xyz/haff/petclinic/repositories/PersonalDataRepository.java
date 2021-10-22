package xyz.haff.petclinic.repositories;

import org.springframework.data.repository.CrudRepository;
import xyz.haff.petclinic.models.PersonalData;

import java.util.Optional;
import java.util.UUID;

public interface PersonalDataRepository extends CrudRepository<PersonalData, UUID> {

    Optional<PersonalData> findByUserId(UUID userId);

    Optional<PersonalData> findByFirstNameAndLastName(String firstName, String lastName);
}
