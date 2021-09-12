package xyz.haff.petclinic.models;

public interface PersistentObject {
    String getId();
    void setId(String id);

    Integer getVersion();
    void setVersion(Integer version);
}
