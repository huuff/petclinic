package xyz.haff.petclinic.models;

public interface BaseEntity {
    String getId();
    void setId(String id);

    Integer getVersion();
    void setVersion(Integer version);
}
