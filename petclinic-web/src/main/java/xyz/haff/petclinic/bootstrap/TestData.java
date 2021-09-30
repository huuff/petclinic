package xyz.haff.petclinic.bootstrap;

import lombok.experimental.UtilityClass;
import xyz.haff.petclinic.models.Owner;

@UtilityClass
public class TestData {
    public static final Owner joeSmith = Owner.builder()
            .firstName("Joe")
            .lastName("Smith")
            .build();

    public static final Owner michaelWeston = Owner.builder()
            .firstName("Michael")
            .lastName("Weston")
            .build();
}
