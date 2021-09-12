package xyz.haff.petclinic.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import xyz.haff.petclinic.repositories.PetTypeRepository;

@RequiredArgsConstructor
@RequestMapping("/pet_types")
@Controller
public class PetTypeController {
    private final PetTypeRepository repository;

    @RequestMapping("/list")
    public String list(Model model) {
        model.addAttribute("pet_types", repository.findAll());

        return "pet_type/list";
    }
}
