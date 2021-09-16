package xyz.haff.petclinic.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import xyz.haff.petclinic.exceptions.NotFoundException;
import xyz.haff.petclinic.models.Owner;
import xyz.haff.petclinic.models.Pet;
import xyz.haff.petclinic.repositories.OwnerRepository;
import xyz.haff.petclinic.repositories.PetRepository;

@RequiredArgsConstructor
@Controller
@RequestMapping("/owners")
public class OwnerController {
    private static final String OWNER_CREATE_OR_UPDATE_FORM = "owners/edit";
    private static final String OWNER_LIST = "owners/list";

    private final OwnerRepository ownerRepository;
    private final PetRepository petRepository;

    @InitBinder("owner")
    public void unbindID(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("owners", ownerRepository.findAll());

        return OWNER_LIST;
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable String id, Model model) {
        model.addAttribute("owner", ownerRepository.findById(id).orElseThrow(NotFoundException::new));

        return OWNER_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/{id}/edit")
    public String saveOrUpdate(@PathVariable String id, @ModelAttribute Owner owner) {
        owner.setId(id);
        ownerRepository.save(owner);

        return "redirect:/" + OWNER_LIST;
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("owner", new Owner());

        return OWNER_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Owner owner) {
        ownerRepository.save(owner);

        return "redirect:/" + OWNER_LIST;
    }

    @GetMapping("/{id}/add_pet")
    public String addPet(@PathVariable String id, Model model) {
        if (!ownerRepository.existsById(id))
            throw new NotFoundException();

        var newPet = new Pet();

        model.addAttribute("pet", newPet);
        return "pets/edit";
    }

    // This in a service?
    @PostMapping("/{ownerId}/add_pet")
    public String addPet(@PathVariable String ownerId, @ModelAttribute Pet pet) {
        var owner = ownerRepository.findById(ownerId).orElseThrow(NotFoundException::new);
        owner.getPets().add(pet);
        pet.setOwner(owner);

        ownerRepository.save(owner);
        petRepository.save(pet);

        return "redirect:/" + OWNER_LIST;
    }
}
