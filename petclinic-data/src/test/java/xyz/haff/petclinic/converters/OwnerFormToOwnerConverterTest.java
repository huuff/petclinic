package xyz.haff.petclinic.converters;

import org.junit.jupiter.api.Test;
import xyz.haff.petclinic.models.forms.OwnerForm;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class OwnerFormToOwnerConverterTest {

    @Test
    void convert() {
        final var FIRST_NAME = "FIRST_NAME";
        final var LAST_NAME = "LAST_NAME";
        final var USERNAME = "USERNAME";
        final var PASSWORD = "PASSWORD";

        var registrationForm = new OwnerForm(FIRST_NAME, LAST_NAME, USERNAME, PASSWORD, PASSWORD);
        var owner = new OwnerFormToOwnerConverter(x -> x).convert(registrationForm);

        assertThat(owner).isNotNull();
        assertThat(owner.getPersonalData().getFirstName()).isEqualTo(FIRST_NAME);
        assertThat(owner.getPersonalData().getLastName()).isEqualTo(LAST_NAME);
        assertThat(owner.getPersonalData().getUser().getUsername()).isEqualTo(USERNAME);
        assertThat(owner.getPersonalData().getUser().getPassword()).isEqualTo(PASSWORD);
    }
}