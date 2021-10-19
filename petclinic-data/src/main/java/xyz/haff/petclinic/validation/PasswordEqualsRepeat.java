package xyz.haff.petclinic.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PersonFormPasswordValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordEqualsRepeat {
    String message() default "{repeat_password_error}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
