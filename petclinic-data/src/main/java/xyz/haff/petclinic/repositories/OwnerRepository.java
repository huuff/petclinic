package xyz.haff.petclinic.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import xyz.haff.petclinic.models.Owner;

import java.util.Optional;
import java.util.UUID;

public interface OwnerRepository extends CrudRepository<Owner, UUID> {

    @Query("SELECT o FROM Owner o " +
            "JOIN PersonalData pd ON pd.id=o.personalData " +
            "JOIN User u ON u.id=pd.user " +
            "WHERE u.id=:userId")
    Optional<Owner> findByUserId(@Param("userId") UUID userId);
}
