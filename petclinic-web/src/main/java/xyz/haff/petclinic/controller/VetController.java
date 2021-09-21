package xyz.haff.petclinic.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import xyz.haff.petclinic.exceptions.NotFoundException;
import xyz.haff.petclinic.models.Vet;
import xyz.haff.petclinic.repositories.VetRepository;
import xyz.haff.petclinic.repositories.VisitRepository;

import javax.validation.Valid;

@Controller
@RequestMapping("/vets")
@RequiredArgsConstructor
public class VetController {
    private final static String LIST_VIEW = "vets/list" ;

    private final VetRepository vetRepository;
    private final VisitRepository visitRepository;

    @InitBinder
    public void unbindID(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("vets", vetRepository.findAll());

        return LIST_VIEW;
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable String id, Model model) {
        // TODO: Don't block
        model.addAttribute("vet", vetRepository.findById(id).blockOptional().orElseThrow(NotFoundException::new));

        return "vets/edit";
    }

    @PostMapping("/{id}/edit")
    public String updateOrCreate(@PathVariable String id, @Valid @ModelAttribute Vet vet, BindingResult bindingResult, Model model) {

        if (!bindingResult.hasErrors()) {
            vet.setId(id);
            vetRepository.save(vet);

            return "redirect:/" + LIST_VIEW;
        } else {
            model.addAttribute("vet", vet);
            return "vets/edit";
        }

    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("vet", new Vet());

        return "vets/edit";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute @Valid Vet vet, BindingResult bindingResult, Model model) {
        if (vetRepository.existsByFirstNameAndLastName(vet.getFirstName(), vet.getLastName())) {
            bindingResult.reject("duplicate");
        }

        if (!bindingResult.hasErrors()) {
            vetRepository.save(vet);
            return "redirect:/" + LIST_VIEW;
        } else {
            model.addAttribute("vet", vet);
            return "vets/edit";
        }
    }

    @GetMapping("/{id}/visits")
    public String visits(@PathVariable String id, Model model) {
        model.addAttribute("visits", visitRepository.findAllByVetId(id));

        return "visits/list";
    }
}
