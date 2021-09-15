package xyz.haff.petclinic.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import xyz.haff.petclinic.models.Vet;
import xyz.haff.petclinic.repositories.VetRepository;

@Controller
@RequestMapping("/vets")
@RequiredArgsConstructor
public class VetController {
    private final static String LIST_VIEW = "vets/list" ;

    private final VetRepository repository;

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("vets", repository.findAll());

        return LIST_VIEW;
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable String id, Model model) {
        // TODO: Handle absence
        model.addAttribute("vet", repository.findById(id));

        return "vets/edit";
    }

    @PostMapping("/edit")
    public String updateOrCreate(@ModelAttribute Vet vet) {
        repository.save(vet);

        return "redirect:/" + LIST_VIEW;
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("vet", new Vet());

        return "vets/edit";
    }
}
