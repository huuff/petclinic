package xyz.haff.petclinic.controllers.global;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import xyz.haff.petclinic.exceptions.GenericNotFoundException;
import xyz.haff.petclinic.exceptions.SpecificNotFoundException;

@ControllerAdvice
@Slf4j
@Profile({"demo", "test"})
public class DevExceptionHandler {

    @ExceptionHandler(SpecificNotFoundException.class)
    public String notFound(Model model, SpecificNotFoundException exception) {
        model.addAttribute("exception", exception);
        log.error("Exception", exception);
        return "error/404-specific";
    }

    @ExceptionHandler(GenericNotFoundException.class)
    public String notFound(GenericNotFoundException exception) {
        log.error("Exception", exception);
        return "error/404";
    }
}
