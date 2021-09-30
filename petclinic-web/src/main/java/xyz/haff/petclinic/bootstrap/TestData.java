package xyz.haff.petclinic.bootstrap;

import lombok.experimental.UtilityClass;
import xyz.haff.petclinic.models.Owner;
import xyz.haff.petclinic.models.Role;
import xyz.haff.petclinic.models.User;

@UtilityClass
public class TestData {
    public static final Owner joeSmith = Owner.builder()
            .firstName("Joe")
            .lastName("Smith")
            .user(User.builder().username("joe").password("{noop}smith").role(Role.OWNER).build())
            .build();

    public static final Owner michaelWeston = Owner.builder()
            .firstName("Michael")
            .lastName("Weston")
            .user(User.builder().username("mike").password("{noop}weston").role(Role.OWNER).build())
            .build();
}
