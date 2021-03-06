package xyz.haff.petclinic.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import xyz.haff.petclinic.exceptions.SpecificNotFoundException;
import xyz.haff.petclinic.models.forms.VisitForm;
import xyz.haff.petclinic.repositories.OwnerRepository;
import xyz.haff.petclinic.repositories.VetRepository;
import xyz.haff.petclinic.security.UserDetailsAdapter;
import xyz.haff.petclinic.services.TitleService;
import xyz.haff.petclinic.services.VisitService;

import javax.validation.Valid;
import java.util.Locale;

@Controller
@RequiredArgsConstructor
@RequestMapping(VisitsController.BASE_PATH)
public class VisitsController {
    public static final String BASE_PATH = "/visits";
    public static final String CREATE_PATH = "/create";

    private final OwnerRepository ownerRepository;
    private final VetRepository vetRepository;
    private final TitleService titleService;
    private final VisitService visitService;

    @GetMapping(VisitsController.CREATE_PATH)
    public String showCreateForm(Model model, Locale locale, @AuthenticationPrincipal Object principal) {
        if (!(principal instanceof UserDetailsAdapter)) {
            throw new AccessDeniedException("Not authenticated");
        }

        var userDetails = (UserDetailsAdapter) principal;
        var owner = ownerRepository
                .findByUserId(userDetails.getUser().getId())
                .orElseThrow(() -> SpecificNotFoundException.fromUserId(userDetails.getUser().getId()));

        titleService.newAppointment(model, locale);

        model.addAttribute("visitForm", new VisitForm());
        model.addAttribute("pets", owner.getPets());
        model.addAttribute("vets", vetRepository.findAll());

        return "visits/edit";
    }

    // TODO: Set a random vet if none is chosen
    @PostMapping(VisitsController.CREATE_PATH)
    public String create(@Valid VisitForm visitForm, BindingResult bindingResult, Model model, Locale locale) {
        if (bindingResult.hasErrors()) {
            titleService.newAppointment(model, locale);
            return "visits/edit";
        }

        visitService.saveVisit(visitForm);

        return "redirect:/";
    }
}
