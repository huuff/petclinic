package xyz.haff.petclinic.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import xyz.haff.petclinic.exceptions.NotFoundException;
import xyz.haff.petclinic.models.Vet;
import xyz.haff.petclinic.repositories.VetRepository;

import javax.validation.Valid;

@Controller
@RequestMapping("/vets")
@RequiredArgsConstructor
public class VetController {
    private final static String LIST_VIEW = "vets/list";
    private final static String EDIT_VIEW = "vets/edit";

    private final VetRepository vetRepository;

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
    public Mono<String> edit(@PathVariable String id, Model model) {
        return vetRepository.findById(id)
                .switchIfEmpty(Mono.error(NotFoundException::new))
                .flatMap((vet) -> {
                    model.addAttribute("vet", vet);
                    return Mono.just(EDIT_VIEW);
                });
    }

    @PostMapping("/{id}/edit")
    public Mono<String> updateOrCreate(@PathVariable String id, @Valid @ModelAttribute Vet vet, BindingResult bindingResult, Model model) {
        if (!bindingResult.hasErrors()) {
            vet.setId(id);
            return vetRepository.save(vet).then(Mono.just("redirect:/" + LIST_VIEW));
        } else {
            model.addAttribute("vet", vet);
            return Mono.just("vets/edit");
        }

    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("vet", new Vet());

        return "vets/edit";
    }

    @PostMapping("/create")
    public Mono<String> create(@ModelAttribute @Valid Vet vet, BindingResult bindingResult, Model model) {
        return vetRepository.findByFirstNameAndLastName(vet.getFirstName(), vet.getLastName())
                .flatMap((foundVet) -> {
                    bindingResult.reject("duplicate");
                    return Mono.just("redirect:/vets/" + foundVet.getId() + "/edit");
                }).switchIfEmpty(!bindingResult.hasErrors()
                        ? vetRepository.save(vet).then(Mono.just("redirect:/" + LIST_VIEW))
                        : Mono.just(EDIT_VIEW)
                );
    }
}
