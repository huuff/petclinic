package xyz.haff.petclinic.services;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;
import xyz.haff.petclinic.models.Vet;
import xyz.haff.petclinic.models.forms.VetForm;
import xyz.haff.petclinic.repositories.VetRepository;

@RequiredArgsConstructor
@Service
public class VetService {
    private final Converter<VetForm, Vet> vetFormToVet;
    private final VetRepository vetRepository;


    public Vet registerVet(VetForm vetForm) {
        var vet = vetFormToVet.convert(vetForm);
        return vetRepository.save(vet);
    }
}
