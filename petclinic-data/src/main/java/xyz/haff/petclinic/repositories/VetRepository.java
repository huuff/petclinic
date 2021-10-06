package xyz.haff.petclinic.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import xyz.haff.petclinic.models.Vet;

import java.util.Optional;
import java.util.UUID;

public interface VetRepository extends CrudRepository<Vet, UUID> {

    @Query("SELECT v FROM Vet v " +
            "JOIN PersonalData pd ON pd.id=v.personalData " +
            "JOIN User u ON u.id=pd.user " +
            "WHERE u.id=:userId")
    Optional<Vet> findByUserId(UUID userId);
}
