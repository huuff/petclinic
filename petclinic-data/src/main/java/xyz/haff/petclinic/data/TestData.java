package xyz.haff.petclinic.data;

import lombok.experimental.UtilityClass;
import xyz.haff.petclinic.models.*;

import java.time.LocalDate;

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
            .specialty(Specialty.OPHTHALMOLOGY)
            .personalData(PersonalData.builder()
                    .firstName("Kenny")
                    .lastName("Wiggins")
                    .user(User.builder().username("ken").password("{noop}wiggins").role(Role.VET).build())
                    .build()
            ).build();

    public static final Vet williamGogler = Vet.builder()
            .specialty(Specialty.SURGERY)
            .personalData(PersonalData.builder()
                    .firstName("William")
                    .lastName("Gogler")
                    .user(User.builder().username("willy").password("{noop}gog").role(Role.VET).build())
                    .build()
            ).build();

    public static final Pet toby = Pet.builder()
            .name("Toby")
            .birthDate(LocalDate.of(2013, 7, 3))
            .type(PetType.DOG)
            .owner(joeSmith)
            .build();

    public static final Pet mittens = Pet.builder()
            .name("Mittens")
            .birthDate(LocalDate.of(2015, 2, 15))
            .type(PetType.CAT)
            .owner(joeSmith)
            .build();

    public static final Visit mittensVisit1 = Visit.builder()
            .date(LocalDate.of(2019, 12, 3))
            .pet(mittens)
            .vet(kennyWiggins)
            .reason("Sneezy kitty")
            .vetComment("Suggestion: Keep cat out of fridge")
            .build();

    public static final Visit mittensVisit2 = Visit.builder()
            .date(LocalDate.of(2019, 12, 4))
            .pet(mittens)
            .vet(kennyWiggins)
            .reason("Too fluffy kitty")
            .vetComment("Suggestion: Shave cat")
            .build();

    public static final Visit mittensVisit3 = Visit.builder()
            .date(LocalDate.of(2019, 12, 5))
            .pet(mittens)
            .vet(kennyWiggins)
            .reason("Bitty kitty")
            .vetComment("Observation: Cat is actually a capybara")
            .build()
            ;
}
