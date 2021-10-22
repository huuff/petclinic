package xyz.haff.petclinic.services;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;
import xyz.haff.petclinic.models.Visit;
import xyz.haff.petclinic.models.forms.VisitForm;
import xyz.haff.petclinic.repositories.VisitRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VisitService {
    private final VisitRepository visitRepository;
    private final Converter<VisitForm, Visit> visitFormToVisit;

    public List<Visit> filterPast(List<Visit> visits) {
        return visits.stream().filter(visit -> visit.getDateTime().isBefore(LocalDateTime.now())).collect(Collectors.toList());
    }

    public List<Visit> filterUpcoming(List<Visit> visits) {
        return visits.stream().filter(visit -> visit.getDateTime().isAfter(LocalDateTime.now())).collect(Collectors.toList());
    }

    public List<Visit> findAllOfOwner(UUID ownerId) {
        return visitRepository.findByPet_OwnerId(ownerId);
    }

    public Visit saveVisit(VisitForm visitForm) {
        return visitRepository.save(visitFormToVisit.convert(visitForm));
    }
}
