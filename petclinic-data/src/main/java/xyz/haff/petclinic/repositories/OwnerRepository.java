package xyz.haff.petclinic.repositories;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import xyz.haff.petclinic.models.Owner;
import xyz.haff.petclinic.models.User;

import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Service
public class OwnerRepository {
    private final DatabaseClient dbClient;
    private final UserRepository userRepository;

    @Transactional
    public Mono<Object> save(Mono<Owner> ownerPublisher) {
        log.info("Calling OwnerRepository.save()");
        return ownerPublisher.flatMap(owner -> userRepository.save(owner.getUser()).flatMap(user -> {
                    if (owner.getVersion() == 0)
                        return dbClient.sql("INSERT INTO owner (id, version, user, first_name, last_name) " +
                                "VALUES (:id, :version, :user, :firstName, :lastName)")
                        .bind("id", owner.getId())
                        .bind("version", 1)
                        .bind("user", user.getId())
                        .bind("firstName", owner.getFirstName())
                        .bind("lastName", owner.getLastName())
                        .then()
                        ;
                    else
                        return Mono.error(RuntimeException::new);
                })
        );
    }

    public Flux<Owner> findAll() {
        return dbClient.sql("SELECT " +
                "o.id as OWNER_ID, " +
                "o.version as OWNER_VERSION, " +
                "o.first_name as FIRST_NAME, " +
                "o.last_name as LAST_NAME, " +
                "u.id as USER_ID, " +
                "u.version as USER_VERSION, " +
                "u.username as USERNAME, " +
                "u.password as PASSWORD " +
                "FROM owner as o JOIN user as u ON o.user=u.id")
                .fetch()
                .all()
                .map(rowSpec -> new Owner(
                        (UUID) rowSpec.get("OWNER_ID"),
                        (Integer) rowSpec.get("OWNER_VERSION"),
                        new User(
                                (UUID) rowSpec.get("USER_ID"),
                                (Integer) rowSpec.get("USER_VERSION"),
                                (String) rowSpec.get("USERNAME"),
                                (String) rowSpec.get("PASSWORD")
                        ),
                        (String) rowSpec.get("FIRST_NAME"),
                        (String) rowSpec.get("LAST_NAME")
                ));
    }

}
