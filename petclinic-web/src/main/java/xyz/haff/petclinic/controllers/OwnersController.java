package xyz.haff.petclinic.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Mono;
import xyz.haff.petclinic.repositories.OwnerRepository;

@Controller
@RequiredArgsConstructor
public class OwnersController {
    private final OwnerRepository ownerRepository;

    @GetMapping("/owners")
    public Mono<String> listAll(Model model) {
        model.addAttribute("owners", ownerRepository.findAll());

        return Mono.just("owners/list");
    }
}
