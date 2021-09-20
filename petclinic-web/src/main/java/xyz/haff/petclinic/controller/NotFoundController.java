package xyz.haff.petclinic.controller;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import xyz.haff.petclinic.exceptions.NotFoundException;

@ControllerAdvice
public class NotFoundController {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected String notFound(NotFoundException exception, Model model) {
        return "errors/404";
    }
}
