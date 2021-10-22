package xyz.haff.petclinic.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import xyz.haff.petclinic.exceptions.SpecificNotFoundException;
import xyz.haff.petclinic.repositories.PersonalDataRepository;
import xyz.haff.petclinic.security.UserDetailsAdapter;
import xyz.haff.petclinic.services.TitleService;

import java.util.Locale;

@RequiredArgsConstructor
@Controller
@RequestMapping(IndexController.PATH)
public class IndexController {
    public static final String PATH = "/";

    private final PersonalDataRepository personalDataRepository;
    private final TitleService titleService;

    @GetMapping
    @PreAuthorize("permitAll()")
    public String index(Model model, @AuthenticationPrincipal Object authenticationPrincipal, Locale locale) {
        if (authenticationPrincipal instanceof UserDetailsAdapter) {
            var userDetails = (UserDetailsAdapter) authenticationPrincipal;
            var personalData = personalDataRepository
                    .findByUserId(userDetails.getUser().getId())
                    .orElseThrow(() -> SpecificNotFoundException.fromUserId(userDetails.getUser().getId()));

            titleService.welcomeAuth(model, locale, personalData);
        } else {
            titleService.welcomeAnon(model, locale);
        }

        return "index";
    }

}
