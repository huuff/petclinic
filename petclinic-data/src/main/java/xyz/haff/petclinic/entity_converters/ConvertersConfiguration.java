package xyz.haff.petclinic.entity_converters;

import io.r2dbc.spi.ConnectionFactory;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import xyz.haff.petclinic.repositories.VetRepository;

import java.util.List;

@RequiredArgsConstructor
@Configuration
public class ConvertersConfiguration extends AbstractR2dbcConfiguration {
    private final ConnectionFactory connectionFactory;

    private final PersonalDataReadConverter personalDataReadConverter;
    private final OwnerReadConverter ownerReadConverter;
    private final VetReadConverter vetReadConverter;

    @NotNull
    @Override
    public ConnectionFactory connectionFactory() {
        return connectionFactory;
    }

    @NotNull
    @Override
    public List<Object> getCustomConverters() {
        return List.of(
                personalDataReadConverter,
                ownerReadConverter,
                vetReadConverter
        );
    }


}
