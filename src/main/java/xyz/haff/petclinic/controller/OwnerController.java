package xyz.haff.petclinic.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import xyz.haff.petclinic.models.Owner;
import xyz.haff.petclinic.repositories.OwnerRepository;

@RequiredArgsConstructor
@Controller
@RequestMapping("/owners")
public class OwnerController {
    private static final String OWNER_CREATE_OR_UPDATE_FORM = "owners/edit";

    private final OwnerRepository repository;

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("owners", repository.findAll());

        return "owners/list";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable String id, Model model) {
        model.addAttribute("owner", repository.findById(id));

        return OWNER_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/edit")
    public String saveOrUpdate(@ModelAttribute Owner owner) {
        var savedOwner = repository.save(owner);

        return "redirect:/owners/list";
    }



}
