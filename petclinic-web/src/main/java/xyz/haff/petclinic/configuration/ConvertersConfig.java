package xyz.haff.petclinic.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import xyz.haff.petclinic.mappers.PetFormToPetMapperImpl;
import xyz.haff.petclinic.mappers.PetToPetFormMapperImpl;

@Configuration
public class ConvertersConfig implements WebMvcConfigurer {

    // TODO: Is this really needed?
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new PetFormToPetMapperImpl());
        registry.addConverter(new PetToPetFormMapperImpl());
    }
}
