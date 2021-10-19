package xyz.haff.petclinic.validation;

import xyz.haff.petclinic.models.forms.PersonForm;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class PersonFormPasswordValidator implements ConstraintValidator<PasswordEqualsRepeat, PersonForm> {

    @Override
    public boolean isValid(PersonForm personForm, ConstraintValidatorContext constraintValidatorContext) {
        boolean isValid = Objects.equals(personForm.getPassword(), personForm.getRepeatPassword());

        if (!isValid) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("").addPropertyNode("password").addConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("{repeat_password_error}").addPropertyNode("repeatPassword").addConstraintViolation();
        }

        return isValid;
    }
}
