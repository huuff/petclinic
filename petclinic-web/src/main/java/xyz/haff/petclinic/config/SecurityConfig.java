package xyz.haff.petclinic.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import xyz.haff.petclinic.repositories.UserRepository;
import xyz.haff.petclinic.security.UserDetailsAdapter;

import java.util.HashMap;
import java.util.function.Function;

@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserRepository userRepository;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests().anyRequest().permitAll() // Use method authentication exclusively
                .and()
                    .formLogin().permitAll()
                .and()
                    .logout().logoutSuccessUrl("/")
                ;
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        return username -> new UserDetailsAdapter(userRepository.findByUsername(username));
    }

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        var defaultEncoder = "bcrypt";
        var encodersMap = new HashMap<String, PasswordEncoder>() {{
            put(defaultEncoder, new BCryptPasswordEncoder());
            put("noop", NoOpPasswordEncoder.getInstance()); // Relax, it's just for demoing
        }};

        return new DelegatingPasswordEncoder(defaultEncoder, encodersMap);
    }

    @Bean
    public Function<String, String> passwordEncodingFunction(PasswordEncoder passwordEncoder) {
        return passwordEncoder::encode;
    }
}
