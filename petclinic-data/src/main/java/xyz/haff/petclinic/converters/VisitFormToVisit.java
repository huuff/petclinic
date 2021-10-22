package xyz.haff.petclinic.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import xyz.haff.petclinic.models.Visit;
import xyz.haff.petclinic.models.forms.VisitForm;

@Component
public class VisitFormToVisit implements Converter<VisitForm, Visit> {
    @Override
    public Visit convert(VisitForm visitForm) {
        return Visit.builder()
                .pet(visitForm.getPet())
                .vet(visitForm.getVet())
                .reason(visitForm.getReason())
                .dateTime(visitForm.getDateTime())
                .build();
    }
}
