package xyz.haff.petclinic.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import xyz.haff.petclinic.repositories.PersonalDataRepository;
import xyz.haff.petclinic.security.UserDetailsAdapter;

@RequiredArgsConstructor
@Controller
@RequestMapping("/")
public class IndexController {
    private final PersonalDataRepository personalDataRepository;

    @GetMapping
    public String index(Model model, @AuthenticationPrincipal UserDetailsAdapter userDetails) {
        model.addAttribute("name", personalDataRepository.findByUserId(userDetails.getUser().getId()).getFirstName());

        return "index";
    }

}
