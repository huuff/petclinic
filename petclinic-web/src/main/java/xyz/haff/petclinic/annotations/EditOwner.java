package xyz.haff.petclinic.annotations;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@PreAuthorize("hasAuthority('VET') OR @userAuthenticationManager.ownerUserMatches(authentication, #ownerId)")
public @interface EditOwner {
}
