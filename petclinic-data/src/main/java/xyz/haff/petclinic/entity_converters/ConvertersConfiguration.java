package xyz.haff.petclinic.entity_converters;

import io.r2dbc.spi.ConnectionFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;

import java.util.List;

@RequiredArgsConstructor
@Configuration
public class ConvertersConfiguration extends AbstractR2dbcConfiguration {
    private final ConnectionFactory connectionFactory;

    @Override
    public ConnectionFactory connectionFactory() {
        return connectionFactory;
    }

    @Override
    public List<Object> getCustomConverters() {
        return List.of(new OwnerReadConverter());
    }


}
