package xyz.haff.petclinic.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import xyz.haff.petclinic.converters.PetFormToPetConverter;
import xyz.haff.petclinic.exceptions.NotFoundException;
import xyz.haff.petclinic.forms.PetForm;
import xyz.haff.petclinic.models.Owner;
import xyz.haff.petclinic.models.Pet;
import xyz.haff.petclinic.repositories.OwnerRepository;
import xyz.haff.petclinic.repositories.PetRepository;

import javax.validation.Valid;

@RequiredArgsConstructor
@Controller
@RequestMapping("/owners")
public class OwnerController {
    private static final String OWNER_EDIT = "owners/edit";
    private static final String OWNER_LIST = "owners/list";

    private final OwnerRepository ownerRepository;
    private final PetRepository petRepository;
    private final PetFormToPetConverter petFormToPetConverter;

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

        return OWNER_EDIT;
    }

    @PostMapping("/{id}/edit")
    public String saveOrUpdate(@PathVariable String id, @ModelAttribute @Valid Owner owner, BindingResult bindingResult, Model model) {
        owner.setId(id);

        if (!bindingResult.hasErrors()) {
            ownerRepository.save(owner);

            return "redirect:/" + OWNER_LIST;
        } else {
            model.addAttribute("owner", owner);

            return OWNER_EDIT;
        }
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("owner", new Owner());

        return OWNER_EDIT;
    }

    @PostMapping("/create")
    public String create(@ModelAttribute @Valid Owner owner, BindingResult bindingResult, Model model) {
        // TODO: Prevent duplicates
        if (!bindingResult.hasErrors()) {
            ownerRepository.save(owner);

            return "redirect:/" + OWNER_LIST;
        } else {
            model.addAttribute("owner", owner);

            return OWNER_EDIT;
        }
    }

    @GetMapping("/{id}/add_pet")
    public String addPet(@PathVariable String id, Model model) {
        if (!ownerRepository.existsById(id))
            throw new NotFoundException();

        var newPet = new PetForm();

        model.addAttribute("pet", newPet);
        return "pets/edit";
    }

    // This in a service?
    @PostMapping("/{ownerId}/add_pet")
    public String addPet(@PathVariable String ownerId, @Valid @ModelAttribute PetForm petForm, BindingResult bindingResult, Model model) {
        var pet = petFormToPetConverter.convert(petForm);

        if (!bindingResult.hasErrors()) {
            var owner = ownerRepository.findById(ownerId).orElseThrow(NotFoundException::new);
            owner.getPets().add(pet);
            pet.setOwner(owner);

            ownerRepository.save(owner);
            petRepository.save(pet);

            return "redirect:/" + OWNER_LIST;
        } else {
            model.addAttribute("pet", pet);
            return "pets/edit";
        }
    }
}
