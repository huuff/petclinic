package xyz.haff.petclinic.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import xyz.haff.petclinic.repositories.VisitRepository;

@Controller
@RequiredArgsConstructor
@RequestMapping("/visits")
public class VisitController {
    private final VisitRepository repository;

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("visits", repository.findAll());

        return "visits/list";
    }
}
