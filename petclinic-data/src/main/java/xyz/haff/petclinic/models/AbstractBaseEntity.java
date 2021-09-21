package xyz.haff.petclinic.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.UUID;

// TODO: Version is definitely not working

@Getter
@Setter
public abstract class AbstractBaseEntity implements BaseEntity {

    @Id
    private String id = UUID.randomUUID().toString();

    private Integer version;

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BaseEntity)) {

            return false;
        }

        BaseEntity other = (BaseEntity)o;
        
        if (id == null) return false;

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