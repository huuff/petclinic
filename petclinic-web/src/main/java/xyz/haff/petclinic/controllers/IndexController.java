package xyz.haff.petclinic.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import xyz.haff.petclinic.repositories.PersonalDataRepository;
import xyz.haff.petclinic.security.UserDetailsAdapter;

import java.util.Optional;

@RequiredArgsConstructor
@Controller
@RequestMapping(IndexController.PATH)
public class IndexController {
    public static final String PATH = "/";

    private final PersonalDataRepository personalDataRepository;

    @GetMapping
    @PreAuthorize("permitAll()")
    public String index(Model model, @AuthenticationPrincipal UserDetailsAdapter userDetails) {
        if (userDetails != null) {
            model.addAttribute("name", personalDataRepository.findByUserId(userDetails.getUser().getId()).getFirstName());
        }

        return "index";
    }

}
