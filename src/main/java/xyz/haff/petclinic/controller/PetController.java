package xyz.haff.petclinic.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import xyz.haff.petclinic.repositories.PetRepository;

@RequiredArgsConstructor
@Controller
@RequestMapping("/pets")
public class PetController {
    private final PetRepository repository;

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("pets", repository.findAll());

        return "pets/list";
    }
}
