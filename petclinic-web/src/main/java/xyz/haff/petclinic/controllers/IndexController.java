package xyz.haff.petclinic.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Mono;
import xyz.haff.petclinic.models.Owner;
import xyz.haff.petclinic.repositories.OwnerRepository;
import xyz.haff.petclinic.security.UserDetailsAdapter;

import java.security.Principal;

@RequiredArgsConstructor
@Controller
public class IndexController {
    private final OwnerRepository ownerRepository;

    @GetMapping({"/", "", "/index.html", "index"})
    public Mono<String> index(Model model, @AuthenticationPrincipal Mono<UserDetailsAdapter> userDetailsPublisher) {
        return userDetailsPublisher
                .doOnNext(userDetailsAdapter -> {
                    var user = userDetailsAdapter.getUser();

                    model.addAttribute("name", ownerRepository.findByUsername(user.getUsername()).map(Owner::getFirstName));
                })
                .then(Mono.just("index"))
                ;
    }
}
