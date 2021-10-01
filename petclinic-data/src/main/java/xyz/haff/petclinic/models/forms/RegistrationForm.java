package xyz.haff.petclinic.models.forms;

import lombok.Data;

@Data
public class RegistrationForm {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
}
