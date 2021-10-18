package xyz.haff.petclinic.controllers;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class AddLanguageControllerAdvice {

    @ModelAttribute
    public void addRouteToModel(Model model, WebRequest webRequest) {
        model.addAttribute("lang", LocaleContextHolder.getLocale().getLanguage());
    }
}
