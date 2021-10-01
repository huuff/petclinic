package xyz.haff.petclinic.converters;

import org.junit.jupiter.api.Test;
import xyz.haff.petclinic.models.forms.RegistrationForm;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class RegistrationFormToOwnerConverterTest {

    @Test
    void convert() {
        final var FIRST_NAME = "FIRST_NAME";
        final var LAST_NAME = "LAST_NAME";
        final var USERNAME = "USERNAME";
        final var PASSWORD = "PASSWORD";

        var registrationForm = new RegistrationForm(FIRST_NAME, LAST_NAME, USERNAME, PASSWORD);
        var owner = new RegistrationFormToOwnerConverter(x -> x).convert(registrationForm);

        assertThat(owner).isNotNull();
        assertThat(owner.getPersonalData().getFirstName()).isEqualTo(FIRST_NAME);
        assertThat(owner.getPersonalData().getLastName()).isEqualTo(LAST_NAME);
        assertThat(owner.getPersonalData().getUser().getUsername()).isEqualTo(USERNAME);
        assertThat(owner.getPersonalData().getUser().getPassword()).isEqualTo(PASSWORD);
    }
}