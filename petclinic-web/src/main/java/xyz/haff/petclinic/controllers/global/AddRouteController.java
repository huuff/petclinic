package xyz.haff.petclinic.controllers.global;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import xyz.haff.petclinic.controllers.Route;

/**
 * Controller advice to get the route and add it to the model for all controllers. This is used by the navbar to
 * show which route is active. Feels a little hacky but eh
 */

@ControllerAdvice
public class AddRouteController {

    @ModelAttribute
    public void addRouteToModel(Model model, WebRequest webRequest) {
        var uri = ((ServletWebRequest) webRequest).getRequest().getRequestURI();

        model.addAttribute("route", getRoute(uri));
    }

    private Route getRoute(String uri) {
        if (uri.contains("pets"))
            return Route.PETS;
        if (uri.contains("owners"))
            return Route.OWNERS;
        else if (uri.contains("profile"))
            return Route.PROFILE;
        else if (uri.contains("vets"))
            return Route.VETS;
        else
            return Route.DEFAULT;
    }
}
