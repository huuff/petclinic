package xyz.haff.petclinic.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import xyz.haff.petclinic.exceptions.NotFoundException;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandlerController {

    @ExceptionHandler(NotFoundException.class)
    public String notFound(Exception exception) {
        log.error(exception.getMessage());
        return "errors/404";
    }
}
