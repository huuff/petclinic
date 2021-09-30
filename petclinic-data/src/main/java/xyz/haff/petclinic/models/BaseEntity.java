package xyz.haff.petclinic.models;

import java.util.UUID;

public interface BaseEntity {
    UUID getId();
    void setId(UUID id);

    Integer getVersion();
    void setVersion(Integer version);
}
