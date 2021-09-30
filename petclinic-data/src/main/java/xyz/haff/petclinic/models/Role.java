package xyz.haff.petclinic.models;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    OWNER, VET;

    @Override
    public String getAuthority() {
        return name();
    }
}
