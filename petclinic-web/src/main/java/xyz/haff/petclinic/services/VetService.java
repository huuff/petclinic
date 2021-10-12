package xyz.haff.petclinic.services;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
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
        var personalData = vet.getPersonalData();

        if (!Strings.isEmpty(vetForm.getFirstName()))
            personalData.setFirstName(vetForm.getFirstName());

        if (!Strings.isEmpty(vetForm.getLastName()))
            personalData.setLastName(vetForm.getLastName());

        if (!Strings.isEmpty(vetForm.getUsername()))
            personalData.getUser().setUsername(vetForm.getUsername());

        if (!Strings.isEmpty(vetForm.getPassword()) && vetForm.passwordEqualsRepeatPassword())
            personalData.getUser().setPassword(vetForm.getPassword());

        if (vetForm.getSpecialty() != null)
            vet.setSpecialty(vetForm.getSpecialty());

        vetRepository.save(vet);
    }
}
