package xyz.haff.petclinic.models;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.util.UUID;

@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@MappedSuperclass
@SuperBuilder
public abstract class AbstractBaseEntity implements BaseEntity {
    @Id
    @ToString.Include
    @Builder.Default
    private UUID id = UUID.randomUUID();
    @Version
    private Integer version;

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BaseEntity)) {

            return false;
        }

        BaseEntity other = (BaseEntity) o;

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
}
