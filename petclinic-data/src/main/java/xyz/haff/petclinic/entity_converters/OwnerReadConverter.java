package xyz.haff.petclinic.entity_converters;

import io.r2dbc.spi.Row;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import xyz.haff.petclinic.models.Owner;
import xyz.haff.petclinic.models.User;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@ReadingConverter
public class OwnerReadConverter implements Converter<Row, Owner> {

    // TODO: Why so yellow?
    @Override
    public Owner convert(@NotNull Row row) {
        return new Owner(
                row.get("OWNER_ID", UUID.class),
                row.get("OWNER_VERSION", Integer.class),
                new User(
                        row.get("USER_ID", UUID.class),
                        row.get("USER_VERSION", Integer.class),
                        row.get("USERNAME", String.class),
                        row.get("PASSWORD", String.class)
                ),
                row.get("FIRST_NAME", String.class),
                row.get("LAST_NAME", String.class)
        );
    }
}
