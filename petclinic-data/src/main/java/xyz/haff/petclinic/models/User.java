package xyz.haff.petclinic.models;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
@SuperBuilder
public class User extends AbstractBaseEntity {
    @Column(unique = true, name = "username")
    @ToString.Include
    private String username;
    @Column(name = "password")
    private String password;
    @ToString.Include
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;
}
