package xyz.haff.petclinic.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
@Profile("prod")
public class ProdGlobalExceptionHandlerController {

    @ExceptionHandler(Exception.class)
    public String allExceptionsTo404(Exception exception) {
        log.error("Exception", exception);
        return "error/404";
    }
}
