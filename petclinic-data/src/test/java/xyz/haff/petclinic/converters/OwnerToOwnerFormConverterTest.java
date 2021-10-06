package xyz.haff.petclinic.converters;

import org.junit.jupiter.api.Test;
import xyz.haff.petclinic.models.Owner;
import xyz.haff.petclinic.models.PersonalData;
import xyz.haff.petclinic.models.User;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class OwnerToOwnerFormConverterTest {

    @Test
    void convertsCorrectly() {
        final var FIRST_NAME = "FIRST_NAME";
        final var LAST_NAME = "LAST_NAME";
        final var USERNAME = "USERNAME";
        final var PASSWORD = "PASSWORD";

        var owner = Owner.builder()
                .personalData(PersonalData.builder()
                                .firstName(FIRST_NAME)
                                .lastName(LAST_NAME)
                                .user(User.builder()
                                        .username(USERNAME)
                                        .password(PASSWORD)
                                        .build()
                                ).build()
                ).build();

        var ownerForm = new OwnerToOwnerFormConverter().convert(owner);

        assertThat(ownerForm).isNotNull();
        assertThat(ownerForm.getFirstName()).isEqualTo(FIRST_NAME);
        assertThat(ownerForm.getLastName()).isEqualTo(LAST_NAME);
        assertThat(ownerForm.getUsername()).isEqualTo(USERNAME);
        assertThat(ownerForm.getPassword()).isEmpty();
        assertThat(ownerForm.getRepeatPassword()).isEmpty();
    }
}