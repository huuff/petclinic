package xyz.haff.petclinic.testing;

import lombok.experimental.UtilityClass;
import org.springframework.stereotype.Service;
import xyz.haff.petclinic.models.*;

@UtilityClass
public class TestData {
    public static final Owner TEST_OWNER = new Owner(
            new PersonalData(
                    "Testname",
                    "Testsurname",
                    new User(
                            new Role("OWNER"), // TODO: Get these roles from some loaded property?
                            "testuser",
                            "testpassword")
            )
    );

    public static final Vet TEST_VET = new Vet(
            new PersonalData(
                    "Testname",
                    "Testsurname",
                    new User(
                            new Role("VET"),
                            "testuser",
                            "testpassword")
            )
    );
}
