package xyz.haff.petclinic.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import xyz.haff.petclinic.exceptions.NotFoundException;
import xyz.haff.petclinic.models.Pet;
import xyz.haff.petclinic.repositories.PetRepository;
import xyz.haff.petclinic.repositories.VisitRepository;

@RequiredArgsConstructor
@Controller
@RequestMapping("/pets")
public class PetController {
    private static final String PETS_LIST = "pets/list";

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

    @PostMapping("/{id}/edit")
    public String saveOrUpdate(@PathVariable String id, @ModelAttribute Pet pet) {
        pet.setId(id);
        petRepository.save(pet);

        // TODO: Redirect to view of pet? Redirect depending on where we come from?
        return "redirect:/owners/list";
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
