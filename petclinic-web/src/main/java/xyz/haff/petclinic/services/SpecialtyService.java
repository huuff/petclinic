package xyz.haff.petclinic.services;

import org.springframework.stereotype.Service;
import xyz.haff.petclinic.models.Specialty;

import java.util.Arrays;
import java.util.List;

@Service
public class SpecialtyService {
    private final List<Specialty> specialties = Arrays.asList(Specialty.values());

    public List<Specialty> findAll() {
        return specialties;
    }
}
