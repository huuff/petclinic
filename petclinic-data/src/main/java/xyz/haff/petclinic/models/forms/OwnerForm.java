package xyz.haff.petclinic.models.forms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OwnerForm {
    @NotNull
    @NotEmpty
    private String firstName;
    @NotNull
    @NotEmpty
    private String lastName;
    @NotNull
    @NotEmpty
    private String username;

    // TODO: Do something to validate these, but if I add @NotNull and @NotEmpty they get applied at update, which I don't want
    private String password;
    private String repeatPassword;

    public boolean passwordEqualsRepeatPassword() {
        return (password == null && repeatPassword == null) || password.equals(repeatPassword);
    }
}
