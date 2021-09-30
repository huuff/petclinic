package xyz.haff.petclinic.entity_converters;

import io.r2dbc.spi.Row;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Component;
import xyz.haff.petclinic.models.BaseEntity;
import xyz.haff.petclinic.models.Owner;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@RequiredArgsConstructor
@ReadingConverter
@Component
public class OwnerReadConverter implements Converter<Row, Owner> {
    private final PersonalDataReadConverter personalDataReadConverter;

    // TODO: Why so yellow?
    @Override
    public Owner convert(@NotNull Row row) {
        return new Owner(
                new BaseEntity(
                        row.get("OWNER_ID", UUID.class),
                        row.get("OWNER_VERSION", Integer.class)
                ),
                personalDataReadConverter.convert(row)
        );
    }
}
