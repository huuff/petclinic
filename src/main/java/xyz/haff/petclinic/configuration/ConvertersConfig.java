package xyz.haff.petclinic.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import xyz.haff.petclinic.converters.PetFormToPetConverter;

@Configuration
public class ConvertersConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new PetFormToPetConverter());
        registry.addConverter(new PetFormToPetConverter());
    }
}
