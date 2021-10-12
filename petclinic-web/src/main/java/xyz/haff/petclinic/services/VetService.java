package xyz.haff.petclinic.services;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;
import xyz.haff.petclinic.exceptions.SpecificNotFoundException;
import xyz.haff.petclinic.models.Vet;
import xyz.haff.petclinic.models.forms.VetForm;
import xyz.haff.petclinic.repositories.VetRepository;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class VetService {
    private final Converter<VetForm, Vet> vetFormToVet;
    private final Converter<Vet, VetForm> vetToVetForm;
    private final VetRepository vetRepository;
    private final PersonalDataService personalDataService;

    public VetForm createForm(UUID vetId) {
        return vetToVetForm.convert(vetRepository.findById(vetId)
        .orElseThrow(() -> SpecificNotFoundException.fromVetId(vetId)));
    }

    public Vet registerVet(VetForm vetForm) {
        var vet = vetFormToVet.convert(vetForm);
        return vetRepository.save(vet);
    }

    // TODO: Highly duplicated from updateOwner
    public void updateVet(UUID vetId, VetForm vetForm) {
        var vet = vetRepository.findById(vetId)
                .orElseThrow(() -> SpecificNotFoundException.fromVetId(vetId));
        vet.setPersonalData(personalDataService.getUpdated(vet.getPersonalData(), vetForm));

        if (vetForm.getSpecialty() != null)
            vet.setSpecialty(vetForm.getSpecialty());

        vetRepository.save(vet);
    }
}
