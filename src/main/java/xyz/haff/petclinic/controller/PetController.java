package xyz.haff.petclinic.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import xyz.haff.petclinic.exceptions.NotFoundException;
import xyz.haff.petclinic.models.Pet;
import xyz.haff.petclinic.repositories.PetRepository;
import xyz.haff.petclinic.repositories.VisitRepository;

import javax.validation.Valid;

@RequiredArgsConstructor
@Controller
@RequestMapping("/pets")
public class PetController {
    private static final String PETS_LIST = "pets/list";
    private static final String PETS_EDIT = "pets/edit";

    private final PetRepository petRepository;
    private final VisitRepository visitRepository;

    @InitBinder
    public void unbindID(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("pets", petRepository.findAll());

        return PETS_LIST;
    }

    // TODO: could edit a random pet if sent to this endpoint, maybe Spring Security would help
    @PostMapping("/{id}/edit")
    public String saveOrUpdate(@PathVariable String id, @Valid @ModelAttribute Pet pet, BindingResult bindingResult, Model model) {
        pet.setId(id);

        // TODO: Redirect to view of pet? Redirect depending on where we come from?
        if (!bindingResult.hasErrors()) {
            petRepository.save(pet);
            return "redirect:/owners/list";
        } else {
            model.addAttribute("pet", pet);
            return "pets/edit";
        }
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable String id, Model model) {
        model.addAttribute("pet", petRepository.findById(id).orElseThrow(NotFoundException::new));

        return "pets/edit";
    }

    @GetMapping("/{id}/visits")
    public String visits(@PathVariable String id, Model model) {
        model.addAttribute("visits", visitRepository.findAllByPetId(id));

        return "visits/list";
    }
}
