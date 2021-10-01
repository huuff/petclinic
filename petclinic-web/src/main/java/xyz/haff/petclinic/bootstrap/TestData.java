package xyz.haff.petclinic.bootstrap;

import lombok.experimental.UtilityClass;
import xyz.haff.petclinic.models.*;

// TODO: This in petclinic-data?

@UtilityClass
public class TestData {
    public static final Owner TEST_OWNER = Owner.builder()
            .personalData(PersonalData.builder()
                    .firstName("Testname")
                    .lastName("Testsurname")
                    .user(User.builder().username("testuser").password("{noop}testpassword").role(Role.OWNER).build())
                    .build()
            ).build();

    public static final Owner joeSmith = Owner.builder()
            .personalData(PersonalData.builder()
                    .firstName("Joe")
                    .lastName("Smith")
                    .user(User.builder().username("joe").password("{noop}smith").role(Role.OWNER).build())
                    .build()
            ).build();

    public static final Owner michaelWeston = Owner.builder()
            .personalData(PersonalData.builder()
                    .firstName("Michael")
                    .lastName("Weston")
                    .user(User.builder().username("mike").password("{noop}weston").role(Role.OWNER).build())
                    .build()
            ).build();

    public static final Vet kennyWiggins = Vet.builder()
            .personalData(PersonalData.builder()
                    .firstName("Kenny")
                    .lastName("Wiggins")
                    .user(User.builder().username("ken").password("{noop}wiggins").role(Role.VET).build())
                    .build()
            ).build();
}