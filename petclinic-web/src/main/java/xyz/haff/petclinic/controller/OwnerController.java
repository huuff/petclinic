package xyz.haff.petclinic.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import xyz.haff.petclinic.exceptions.NotFoundException;
import xyz.haff.petclinic.forms.PetForm;
import xyz.haff.petclinic.mappers.PetFormToPetMapper;
import xyz.haff.petclinic.models.Owner;
import xyz.haff.petclinic.repositories.OwnerRepository;

import javax.validation.Valid;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
@RequestMapping("/owners")
public class OwnerController {
    private static final String OWNER_EDIT = "owners/edit";
    private static final String OWNER_LIST = "owners/list";

    private final OwnerRepository ownerRepository;
    private final PetFormToPetMapper petFormToPetMapper;

    // TODO: Maybe just use form objects?
    @InitBinder("owner")
    public void unbindID(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("owners", ownerRepository.findAll());

        return OWNER_LIST;
    }

    // TODO: Do not block
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable String id, Model model) {
        Optional<Owner> owner = ownerRepository.findById(id).blockOptional();

        model.addAttribute("owner",  owner.orElseThrow(NotFoundException::new));

        return OWNER_EDIT;
    }

    @PostMapping("/{id}/edit")
    public String saveOrUpdate(@PathVariable String id, @ModelAttribute @Valid Owner owner, BindingResult bindingResult, Model model) {
        owner.setId(id);

        if (!bindingResult.hasErrors()) {
            ownerRepository.save(owner);

            return "redirect:/" + OWNER_LIST;
        } else {
            model.addAttribute("owner", owner);

            return OWNER_EDIT;
        }
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("owner", new Owner());

        return OWNER_EDIT;
    }

    @PostMapping("/create")
    public Mono<String> create(@ModelAttribute @Valid Owner owner, BindingResult bindingResult, Model model) {
        return ownerRepository.existsByFirstNameAndLastName(owner.getFirstName(), owner.getLastName())
                .doOnNext((exists) -> {
                    if (exists)
                        bindingResult.reject("duplicate");
                }).then(!bindingResult.hasErrors()
                        ? ownerRepository.save(owner).thenReturn("redirect:/" + OWNER_LIST)
                        : Mono.create((sink) -> model.addAttribute("owner", owner)).thenReturn(OWNER_EDIT)
                );
    }

    @GetMapping("/{id}/add_pet")
    public String addPet(@PathVariable String id, Model model) {
        // TODO: don't block
        if (!ownerRepository.existsById(id).block())
            throw new NotFoundException();

        var newPet = new PetForm();

        model.addAttribute("petForm", newPet);
        return "pets/edit";
    }

    // This in a service?
    @PostMapping("/{ownerId}/add_pet")
    public String addPet(@PathVariable String ownerId, @Valid @ModelAttribute PetForm petForm, BindingResult bindingResult, Model model) {
        // TODO: don't block
        var owner = ownerRepository.findById(ownerId).blockOptional().orElseThrow(NotFoundException::new);

        if (owner.getPets().stream().anyMatch(pet -> pet.getName().equals(petForm.getName()) && pet.getBirthDate().equals(petForm.getBirthDate())))
            bindingResult.reject("duplicate");

        var pet = petFormToPetMapper.convert(petForm);
        if (!bindingResult.hasErrors()) {

            owner.getPets().add(pet);

            ownerRepository.save(owner);

            return "redirect:/" + OWNER_LIST;
        } else {
            model.addAttribute("petForm", petForm);
            return "pets/edit";
        }
    }
}
