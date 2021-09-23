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

    @GetMapping("/{id}/edit")
    public Mono<String> edit(@PathVariable String id, Model model) {
        return ownerRepository.findById(id)
                .switchIfEmpty(Mono.error(NotFoundException::new))
                .flatMap((owner) -> {
                    model.addAttribute("owner", owner);

                    return Mono.just(OWNER_EDIT);
                });
    }

    @PostMapping("/{id}/edit")
    public Mono<String> saveOrUpdate(@PathVariable String id, @ModelAttribute @Valid Owner owner, BindingResult bindingResult, Model model) {
        if (!bindingResult.hasErrors()) {
            owner.setId(id);
            return ownerRepository.save(owner).then(Mono.just("redirect:/" + OWNER_LIST));
        } else {
            model.addAttribute("owner", owner);
            return Mono.just(OWNER_EDIT);
        }
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("owner", new Owner());

        return OWNER_EDIT;
    }

    @PostMapping("/create")
    public Mono<String> create(@ModelAttribute @Valid Owner owner, BindingResult bindingResult, Model model) {
        // TODO: Prevent duplicates here
        if (!bindingResult.hasErrors()) {
            return ownerRepository.save(owner).thenReturn("redirect:/" + OWNER_LIST);
        } else {
            model.addAttribute("owner", owner);
            return Mono.just(OWNER_EDIT);
        }
    }

    @GetMapping("/{id}/add_pet")
    public Mono<String> addPet(@PathVariable String id, Model model) {
        return ownerRepository.existsById(id)
                .flatMap((exists) -> {
                    if (!exists)
                        return Mono.error(NotFoundException::new);

                    var newPet = new PetForm();
                    model.addAttribute("petForm", newPet);
                    return Mono.just("pets/edit");
                });
    }

    // This in a service?
    @PostMapping("/{ownerId}/add_pet")
    public Mono<String> addPet(@PathVariable String ownerId, @Valid @ModelAttribute PetForm petForm, BindingResult bindingResult, Model model) {
        return ownerRepository.findById(ownerId)
                .switchIfEmpty(Mono.error(NotFoundException::new))
                .doOnNext((owner) -> {
                    if (owner.getPets().stream().anyMatch(pet -> pet.getName().equals(petForm.getName()))) {
                        bindingResult.reject("duplicate");
                    }
                })
                .flatMap((owner) -> {
                    if (!bindingResult.hasErrors()) {
                        owner.getPets().add(petFormToPetMapper.convert(petForm)); // TODO: Try to autoconvert
                        return ownerRepository.save(owner).then(Mono.just("redirect:/" + OWNER_LIST));
                    } else {
                        model.addAttribute("petForm", petForm);
                        return Mono.just("pets/edit");
                    }
                });
    }
}
