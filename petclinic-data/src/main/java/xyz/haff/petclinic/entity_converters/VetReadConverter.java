package xyz.haff.petclinic.entity_converters;

import io.r2dbc.spi.Row;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Component;
import xyz.haff.petclinic.models.BaseEntity;
import xyz.haff.petclinic.models.Vet;

import java.util.UUID;

@RequiredArgsConstructor
@Component
@ReadingConverter
public class VetReadConverter implements Converter<Row, Vet> {
    private final PersonalDataReadConverter personalDataReadConverter;

    // TODO: Y SO YELLO?????

    @Override
    public Vet convert(Row row) {
        return new Vet(
                new BaseEntity(
                row.get("VET_ID", UUID.class),
                row.get("VET_VERSION", Integer.class)
                ),
               personalDataReadConverter.convert(row)
        );
    }
}
