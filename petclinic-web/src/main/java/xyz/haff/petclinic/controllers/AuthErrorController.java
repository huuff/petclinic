package xyz.haff.petclinic.controllers;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Profile({"demo", "test"})
public class AuthErrorController {

    @RequestMapping("/unauth")
    public String redirectToErrorPage() {
        return "error/403";
    }
}
