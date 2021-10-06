package xyz.haff.petclinic.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import xyz.haff.petclinic.exceptions.NotFoundException;
import xyz.haff.petclinic.repositories.VetRepository;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping(VetController.BASE_PATH)
public class VetController {
    public static final String BASE_PATH = "/vets";
    public static final String LIST_VIEW = "vets/list";
    public static final String READ_VIEW = "vets/view";

    private final VetRepository vetRepository;

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
}
