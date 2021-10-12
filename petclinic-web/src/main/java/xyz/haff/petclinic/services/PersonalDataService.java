package xyz.haff.petclinic.services;

import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;
import xyz.haff.petclinic.models.PersonalData;
import xyz.haff.petclinic.models.forms.PersonForm;

@Service
public class PersonalDataService {

    public PersonalData getUpdated(PersonalData personalData, PersonForm personForm) {
        if (!Strings.isEmpty(personForm.getFirstName()))
            personalData.setFirstName(personForm.getFirstName());

        if (!Strings.isEmpty(personForm.getLastName()))
            personalData.setLastName(personForm.getLastName());

        if (!Strings.isEmpty(personForm.getUsername()))
            personalData.getUser().setUsername(personForm.getUsername());

        if (!Strings.isEmpty(personForm.getPassword()) && personForm.passwordEqualsRepeatPassword())
            personalData.getUser().setPassword(personForm.getPassword());

        return personalData;
    }
}
