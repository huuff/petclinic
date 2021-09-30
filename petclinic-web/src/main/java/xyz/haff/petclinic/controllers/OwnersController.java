package xyz.haff.petclinic.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import xyz.haff.petclinic.repositories.OwnerRepository;

@Controller
@RequiredArgsConstructor
@RequestMapping("/owners")
public class OwnersController {
    private final OwnerRepository ownerRepository;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("owners", ownerRepository.findAll());

        return "owners/list";
    }

}
