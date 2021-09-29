package xyz.haff.petclinic.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Mono;
import xyz.haff.petclinic.repositories.OwnerRepository;
import xyz.haff.petclinic.security.UserDetailsAdapter;

@RequiredArgsConstructor
@Controller
public class IndexController {
    private final OwnerRepository ownerRepository;

    @GetMapping({"/", "", "/index.html", "index"})
    public Mono<String> index(Model model, @AuthenticationPrincipal Mono<UserDetailsAdapter> userDetailsPublisher) {
        return userDetailsPublisher
                .map(UserDetailsAdapter::getUser)
                .doOnNext(user -> {
                    model.addAttribute("name", "TODO");
                })
                .then(Mono.just("index"))
                ;
    }
}
