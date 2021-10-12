package xyz.haff.petclinic.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import xyz.haff.petclinic.exceptions.GenericNotFoundException;
import xyz.haff.petclinic.exceptions.SpecificNotFoundException;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandlerController {

    @ExceptionHandler(SpecificNotFoundException.class)
    public String notFound(Model model, SpecificNotFoundException exception) {
        model.addAttribute("exception", exception);
        exception.printStackTrace();
        return "errors/404-specific";
    }

    @ExceptionHandler(GenericNotFoundException.class)
    public String notFound(GenericNotFoundException exception) {
        return "errors/404-generic";
    }
}
