package xyz.haff.petclinic.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xyz.haff.petclinic.exceptions.NotFoundException;
import xyz.haff.petclinic.models.forms.PersonForm;
import xyz.haff.petclinic.models.forms.VetForm;
import xyz.haff.petclinic.repositories.VetRepository;
import xyz.haff.petclinic.services.PersonFormValidationService;
import xyz.haff.petclinic.services.RegisterService;
import xyz.haff.petclinic.services.VetService;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping(VetsController.BASE_PATH)
public class VetsController {
    public static final String BASE_PATH = "/vets";
    public static final String DELETE_PATH = "/{vetId}/delete";
    public static final String CREATE_PATH = "/create";
    public static final String LIST_VIEW = "vets/list";
    public static final String READ_VIEW = "vets/view";
    public static final String EDIT_VIEW = "vets/edit";

    private final VetRepository vetRepository;
    private final VetService vetService;
    private final RegisterService registerService;
    private final PersonFormValidationService personFormValidationService;

    @GetMapping
    @PreAuthorize("hasAuthority('VET')")
    public String list(Model model) {
        model.addAttribute("vets", vetRepository.findAll());

        return LIST_VIEW;
    }

    @GetMapping("/{vetId}")
    @PreAuthorize("hasAuthority('VET')")
    public String read(@PathVariable UUID vetId, Model model) {
        model.addAttribute("vet", vetRepository.findById(vetId).orElseThrow(NotFoundException::new));

        return READ_VIEW;
    }

    @GetMapping(DELETE_PATH)
    @PreAuthorize("hasAuthority('VET')")
    public String delete(@PathVariable UUID vetId) {
        vetRepository.deleteById(vetId);

        return "redirect:" + BASE_PATH;
    }

    @GetMapping(CREATE_PATH)
    @PreAuthorize("hasAuthority('VET')")
    public String showCreateForm(Model model) {
        model.addAttribute("vetForm", new VetForm());

        return EDIT_VIEW;
    }

    @PostMapping(CREATE_PATH)
    @PreAuthorize("hasAuthority('VET')")
    public String create(@Validated(PersonForm.CreationConstraintGroup.class) @ModelAttribute VetForm vetForm, BindingResult bindingResult) {
        if (!personFormValidationService.checkNewIsValid(vetForm, bindingResult))
            return EDIT_VIEW;

        registerService.registerVet(vetForm);

        return "redirect:" + BASE_PATH;
    }
}
