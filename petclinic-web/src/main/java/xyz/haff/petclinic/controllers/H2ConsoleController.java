package xyz.haff.petclinic.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Controller
@Profile("demo")
public class H2ConsoleController {

    @Value("${xyz.haff.petclinic.h2-console-port}")
    private Integer h2ConsolePort;

    @GetMapping("${spring.h2.console.path}")
    @PreAuthorize("permitAll()")
    public Mono<String> h2Console() {
        return Mono.just("redirect:http://localhost:" + h2ConsolePort.toString());
    }
}
