package xyz.haff.petclinic.entity_converters;

import io.r2dbc.spi.Row;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import xyz.haff.petclinic.models.BaseEntity;
import xyz.haff.petclinic.models.PersonalData;
import xyz.haff.petclinic.models.User;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Component
public class PersonalDataReadConverter implements Converter<Row, PersonalData> {
    private final UserReadConverter userReadConverter;

    @Override
    public PersonalData convert(@NotNull Row row) {
        return new PersonalData(
                new BaseEntity(
                        row.get("PD_ID", UUID.class),
                        row.get("PD_VERSION", Integer.class)
                ),
                row.get("PD_FIRST_NAME", String.class),
                row.get("PD_LAST_NAME", String.class),
                userReadConverter.convert(row)
        );
    }
}
