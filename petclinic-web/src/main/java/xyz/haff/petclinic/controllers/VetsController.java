package xyz.haff.petclinic.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xyz.haff.petclinic.exceptions.SpecificNotFoundException;
import xyz.haff.petclinic.models.forms.CreationConstraintGroup;
import xyz.haff.petclinic.models.forms.PersonForm;
import xyz.haff.petclinic.models.forms.VetForm;
import xyz.haff.petclinic.repositories.VetRepository;
import xyz.haff.petclinic.security.UserDetailsAdapter;
import xyz.haff.petclinic.services.PersonFormValidationService;
import xyz.haff.petclinic.services.VetService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping(VetsController.BASE_PATH)
public class VetsController {
    public static final String BASE_PATH = "/vets";
    public static final String CREATE_PATH = "/create";
    public static final String DELETE_PATH = "/{vetId}/delete";
    public static final String UPDATE_PATH = "/{vetId}/update";
    public static final String LIST_VIEW = "vets/list";
    public static final String READ_VIEW = "vets/view";
    public static final String EDIT_VIEW = "vets/edit";

    private final VetRepository vetRepository;
    private final VetService vetService;
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
        model.addAttribute("vet", vetRepository.findById(vetId)
                .orElseThrow(() -> SpecificNotFoundException.fromVetId(vetId)));

        return READ_VIEW;
    }

    @GetMapping(DELETE_PATH)
    @PreAuthorize("hasAuthority('VET')")
    public String delete(@AuthenticationPrincipal UserDetailsAdapter userDetails,
                         @PathVariable UUID vetId,
                         HttpServletRequest request) throws ServletException {
        var vet = vetRepository.findById(vetId).orElseThrow(() -> SpecificNotFoundException.fromVetId(vetId));

        vetRepository.deleteById(vetId);

        if (vet.getPersonalData().getUser().getId().equals(userDetails.getUser().getId())) {
            request.logout();
            return "redirect:/";
        }

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
    public String create(@Validated(CreationConstraintGroup.class) @ModelAttribute VetForm vetForm, BindingResult bindingResult) {
        if (!personFormValidationService.checkNewIsValid(vetForm, bindingResult))
            return EDIT_VIEW;

        vetService.registerVet(vetForm);

        return "redirect:" + BASE_PATH;
    }

    @GetMapping(UPDATE_PATH)
    @PreAuthorize("hasAuthority('VET')")
    public String showEditForm(@PathVariable UUID vetId, Model model) {
        model.addAttribute("vetForm", vetService.createForm(vetId));

        return EDIT_VIEW;
    }

    @PostMapping(UPDATE_PATH)
    @PreAuthorize("hasAuthority('VET')")
    public String edit(@PathVariable UUID vetId, @Valid VetForm vetForm, BindingResult bindingResult) {
        var editingPerson = vetRepository.findById(vetId)
                .orElseThrow(() -> SpecificNotFoundException.fromVetId(vetId))
                .getPersonalData();

        personFormValidationService.checkEditIsValid(editingPerson, vetForm, bindingResult);

        if (bindingResult.hasErrors())
            return EDIT_VIEW;

        vetService.updateVet(vetId, vetForm);

        return "redirect:" + BASE_PATH + "/" + vetId;
    }
}
