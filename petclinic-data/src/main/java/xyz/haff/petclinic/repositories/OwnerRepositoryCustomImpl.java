package xyz.haff.petclinic.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import xyz.haff.petclinic.models.Owner;
import xyz.haff.petclinic.models.User;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class OwnerRepositoryCustomImpl implements OwnerRepositoryCustom<Owner> {
    private final DatabaseClient dbClient;
    private final UserRepository userRepository;

    @Override
    public <S extends Owner> Mono<S> save(S owner) {
        return userRepository.save(owner.getUser()).flatMap(user -> {
            if (owner.getVersion() == 0)
                return dbClient.sql("INSERT INTO owner (id, version, user, first_name, last_name) " +
                        "VALUES (:id, :version, :user, :firstName, :lastName)")
                        .bind("id", owner.getId())
                        .bind("version", 1)
                        .bind("user", user.getId())
                        .bind("firstName", owner.getFirstName())
                        .bind("lastName", owner.getLastName())
                        .then().then(Mono.just(owner))
                        ;
            else
                return Mono.error(RuntimeException::new);
        });
    }
}
