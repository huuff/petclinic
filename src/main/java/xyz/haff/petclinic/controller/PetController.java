package xyz.haff.petclinic.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import xyz.haff.petclinic.converters.PetFormToPetConverter;
import xyz.haff.petclinic.converters.PetToPetFormConverter;
import xyz.haff.petclinic.exceptions.NotFoundException;
import xyz.haff.petclinic.forms.PetForm;
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
    private final PetToPetFormConverter petToPetFormConverter;

    @InitBinder
    public void unbindID(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("pets", petRepository.findAll());

        return PETS_LIST;
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable String id, Model model) {
        var pet = petRepository.findById(id).orElseThrow(NotFoundException::new);

        model.addAttribute("petForm", petToPetFormConverter.convert(pet) );

        return "pets/edit";
    }

    // TODO: could edit a random pet if sent to this endpoint, maybe Spring Security would help
    @PostMapping("/{id}/edit")
    public String update(@PathVariable String id, @Valid @ModelAttribute PetForm petForm, BindingResult bindingResult, Model model) {
        var pet = petRepository.findById(id).orElseThrow(NotFoundException::new);

        // TODO: Redirect to view of pet? Redirect depending on where we come from?
        if (!bindingResult.hasErrors()) {
            // TODO: The form doesn't bring the owner or the ID, so I can't use the converter. Is there a better way
            // that won't clutter my controller with conversion code?
            pet.setType(petForm.getType());
            pet.setBirthDate(petForm.getBirthDate());
            pet.setName(petForm.getName());
            petRepository.save(pet);
            return "redirect:/owners/list";
        } else {
            model.addAttribute("petForm", petForm);
            return "pets/edit";
        }
    }

    @GetMapping("/{id}/visits")
    public String visits(@PathVariable String id, Model model) {
        model.addAttribute("visits", visitRepository.findAllByPetId(id));

        return "visits/list";
    }
}
