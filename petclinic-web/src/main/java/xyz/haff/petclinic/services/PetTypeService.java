package xyz.haff.petclinic.services;

import org.springframework.stereotype.Service;
import xyz.haff.petclinic.models.PetType;

import java.util.Arrays;
import java.util.List;

@Service
public class PetTypeService {
    private final List<PetType> petTypes = Arrays.asList(PetType.values());

    public List<PetType> findAll() {
        return petTypes;
    }
}
