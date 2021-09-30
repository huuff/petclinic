package xyz.haff.petclinic.entity_converters;

import io.r2dbc.spi.Row;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Component;
import xyz.haff.petclinic.models.Role;

import java.util.UUID;

/**
 * XXX: This is not added to the `ConvertersConfiguration` because the `RoleRepository` is vanilla Spring data without
 * custom modifications. Thus it would interfere with the custom mapping it provides. I use it however, for mapping
 * roles inside other converters. Maybe this is confusing?
 */

@ReadingConverter
@Component
public class RoleReadConverter implements Converter<Row, Role> {

    @Override
    public Role convert(Row row) {
        return new Role(
                row.get("R_ID", UUID.class),
                row.get("R_VERSION", Integer.class),
                row.get("R_NAME", String.class)
        );
    }
}
