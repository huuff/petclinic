package xyz.haff.petclinic.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;

import java.util.UUID;

@With
@Value
public class JavaOwner {
    @Id String id = UUID.randomUUID().toString();
    @Version Integer version = 0;
    @NonNull String firstName;
    @NonNull String lastName;
}
