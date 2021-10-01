package xyz.haff.petclinic.repositories;

import org.springframework.data.repository.CrudRepository;
import xyz.haff.petclinic.models.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends CrudRepository<User, UUID> {

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);
}
