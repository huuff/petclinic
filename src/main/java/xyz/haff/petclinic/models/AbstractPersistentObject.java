package xyz.haff.petclinic.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

// TODO: Try to make these properties final
// TODO: Call it `AbstractBaseEntity`

@MappedSuperclass
public abstract class AbstractPersistentObject implements PersistentObject {

    @Id
    @Getter
    @Setter
    @Column(name = "id")
    private String id = UUID.randomUUID().toString();

    @Getter
    @Setter
    private Integer version;

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PersistentObject)) {

            return false;
        }

        PersistentObject other
                = (PersistentObject)o;

        // if the id is missing, return false
        if (id == null) return false;

        // equivalence by id
        return id.equals(other.getId());
    }

    public int hashCode() {
        if (id != null) {
            return id.hashCode();
        } else {
            return super.hashCode();
        }
    }

    public String toString() {
        return this.getClass().getName()
                + "[id=" + id + "]";
    }
}