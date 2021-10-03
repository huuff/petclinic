package xyz.haff.petclinic.controllers;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import xyz.haff.petclinic.exceptions.NotFoundException;

@ControllerAdvice
public class GlobalExceptionHandlerController {

    @ExceptionHandler(NotFoundException.class)
    public String notFound() {
        return "errors/404";
    }
}
