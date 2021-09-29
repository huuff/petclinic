package xyz.haff.petclinic.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import xyz.haff.petclinic.repositories.UserRepository;
import xyz.haff.petclinic.security.UserDetailsAdapter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@RequiredArgsConstructor
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfig {
    private final UserRepository userRepository;

    @Value("${xyz.haff.petclinic.h2-console-port}")
    private Integer h2Port;

    @Value("${spring.h2.console.path}")
    private String h2ConsolePath;

    @Bean
    public ReactiveUserDetailsService userDetailsService() {
        return (username) -> userRepository.findByUsername(username).map(UserDetailsAdapter::new);
    }

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
                .authorizeExchange(exchanges ->
                        exchanges
                                .pathMatchers(h2ConsolePath).permitAll() // TODO: See if I could ever get method security to work
                                .matchers(portMatcher(h2Port)).permitAll()
                                .anyExchange().authenticated()
                )
                .httpBasic(withDefaults())
                .formLogin(withDefaults());
        return http.build();
    }

    private ServerWebExchangeMatcher portMatcher(int port) {
        return (exchange) -> {
            var localAddress = exchange.getRequest().getLocalAddress();

            if (localAddress == null)
                return ServerWebExchangeMatcher.MatchResult.notMatch();

            if (localAddress.getPort() == port)
                return ServerWebExchangeMatcher.MatchResult.match();
            else
                return ServerWebExchangeMatcher.MatchResult.notMatch();
        };
    }
}
