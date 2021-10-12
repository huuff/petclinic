package xyz.haff.petclinic.services;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import xyz.haff.petclinic.exceptions.NotFoundException;
import xyz.haff.petclinic.models.Vet;
import xyz.haff.petclinic.models.forms.VetForm;
import xyz.haff.petclinic.repositories.PersonalDataRepository;
import xyz.haff.petclinic.repositories.UserRepository;
import xyz.haff.petclinic.repositories.VetRepository;

import java.util.UUID;

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
