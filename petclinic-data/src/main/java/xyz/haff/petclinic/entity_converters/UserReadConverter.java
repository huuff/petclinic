package xyz.haff.petclinic.entity_converters;

import io.r2dbc.spi.Row;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Component;
import xyz.haff.petclinic.models.BaseEntity;
import xyz.haff.petclinic.models.Role;
import xyz.haff.petclinic.models.User;

import java.util.UUID;

@Component
@ReadingConverter
@RequiredArgsConstructor
public class UserReadConverter implements Converter<Row, User> {
    private final RoleReadConverter roleReadConverter;

    @Override
    public User convert(Row row) {
        return new User(
                new BaseEntity(
                row.get("U_ID", UUID.class),
                row.get("U_VERSION", Integer.class)
                ),
                roleReadConverter.convert(row),
                row.get("U_USERNAME", String.class),
                row.get("U_PASSWORD", String.class)
        );
    }
}
