package xyz.haff.petclinic.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import xyz.haff.petclinic.models.Owner;
import xyz.haff.petclinic.models.Pet;
import xyz.haff.petclinic.repositories.OwnerRepository;

@RequiredArgsConstructor
@Controller
@RequestMapping("/owners")
public class OwnerController {
    private static final String OWNER_CREATE_OR_UPDATE_FORM = "owners/edit";
    private static final String OWNER_LIST = "owners/list";

    private final OwnerRepository repository;

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("owners", repository.findAll());

        return OWNER_LIST;
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable String id, Model model) {
        model.addAttribute("owner", repository.findById(id));

        return OWNER_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/{id}/edit")
    public String saveOrUpdate(@PathVariable String id, @ModelAttribute Owner owner) {
        owner.setId(id);
        repository.save(owner);

        return "redirect:/" + OWNER_LIST;
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("owner", new Owner());

        return OWNER_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Owner owner) {
        repository.save(owner);

        return "redirect:/" + OWNER_LIST;
    }



    @GetMapping("/{id}/add_pet")
    public String list(@PathVariable String id, Model model) {
        var newPet = new Pet();
        // TODO: Handle absence of owner
        newPet.setOwner(repository.findById(id).get());

        model.addAttribute("pet", newPet);

        return "pets/edit";
    }



}
