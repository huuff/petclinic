package xyz.haff.petclinic.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import xyz.haff.petclinic.repositories.OwnerRepository;

@RequiredArgsConstructor
@Controller
@RequestMapping("/owners")
public class OwnerController {
    private final OwnerRepository repository;

    @RequestMapping("/list")
    public String list(Model model) {
        model.addAttribute("owners", repository.findAll());

        return "owners/list";
    }

}
