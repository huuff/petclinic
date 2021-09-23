package xyz.haff.petclinic.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import xyz.haff.petclinic.exceptions.NotFoundException;
import xyz.haff.petclinic.models.Owner;
import xyz.haff.petclinic.models.Pet;
import xyz.haff.petclinic.repositories.OwnerRepository;

import javax.validation.Valid;

@RequiredArgsConstructor
@Controller
@RequestMapping("/owners")
public class OwnerController {
    private static final String OWNER_EDIT = "owners/edit";
    private static final String OWNER_LIST = "owners/list";

    private final OwnerRepository ownerRepository;
    
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
            return ownerRepository.findById(id).flatMap((foundOwner) -> { // TODO: Some kind of merge operation?
                foundOwner.setFirstName(owner.getFirstName());
                foundOwner.setLastName(owner.getLastName());
                return ownerRepository.save(foundOwner).then(Mono.just("redirect:/" + OWNER_LIST));
            });
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
        return ownerRepository.findByFirstNameAndLastName(owner.getFirstName(), owner.getLastName())
                .flatMap((found) -> {
                    bindingResult.reject("duplicate");
                    return Mono.just("redirect:/owners/" + found.getId() + "/edit");
                }).switchIfEmpty(!bindingResult.hasErrors()
                        ? ownerRepository.save(owner).thenReturn("redirect:/" + OWNER_LIST)
                        : Mono.just(OWNER_EDIT)
                );
    }

    // TODO: This in a service?
    @GetMapping("/{ownerId}/pets/{name}")
    public Mono<String> viewPet(@PathVariable String ownerId, @PathVariable String name, Model model) {
        return ownerRepository.findById(ownerId)
                .switchIfEmpty(Mono.error(NotFoundException::new))
                .flatMapMany(owner -> Flux.fromIterable(owner.getPets()))
                .filter(pet -> pet.getName().equals(name))
                .switchIfEmpty(Mono.error(NotFoundException::new))
                .next()
                .flatMap((pet) -> {
                    model.addAttribute("pet", pet);
                    return Mono.just("pets/edit");
                });
    }

    // TODO: This in a service
    @PostMapping("/{ownerId}/pets/{name}")
    public Mono<String> updatePet(@PathVariable String ownerId, @PathVariable String name, @ModelAttribute @Valid Pet pet, BindingResult bindingResult, Model model) {
        return ownerRepository.findById(ownerId)
                .switchIfEmpty(Mono.error(NotFoundException::new))
                .zipWhen((owner) ->
                    Flux.fromIterable(owner.getPets())
                        .filter((ownerPet) -> ownerPet.getName().equals(name))
                        .switchIfEmpty(Mono.error(NotFoundException::new))
                        .next()
                ).flatMap((ownerAndPet) -> {
                    if (!bindingResult.hasErrors()) {
                        ownerAndPet.getT2().setName(pet.getName());
                        ownerAndPet.getT2().setType(pet.getType());
                        ownerAndPet.getT2().setBirthDate(pet.getBirthDate());
                        return ownerRepository.save(ownerAndPet.getT1()).then(Mono.just("redirect:/owners/" + ownerId + "/pets/" + pet.getName()));
                    } else {
                        model.addAttribute("pet", pet);
                        return Mono.just("pets/edit");
                    }
                });
    }

    // TODO: Maybe /pets/new?
    @GetMapping("/{id}/add_pet")
    public Mono<String> addPet(@PathVariable String id, Model model) {
        return ownerRepository.existsById(id)
                .flatMap((exists) -> {
                    if (!exists)
                        return Mono.error(NotFoundException::new);

                    var newPet = new Pet();
                    model.addAttribute("isNew", true);
                    model.addAttribute("pet", newPet);
                    return Mono.just("pets/edit");
                });
    }

    // This in a service?
    @PostMapping("/{ownerId}/add_pet")
    public Mono<String> addPet(@PathVariable String ownerId, @Valid @ModelAttribute Pet pet, BindingResult bindingResult, Model model) {
        return ownerRepository.findById(ownerId)
                .switchIfEmpty(Mono.error(NotFoundException::new))
                .flatMap((owner) -> {
                    if (owner.getPets().stream().anyMatch(ownerPet -> ownerPet.getName().equals(pet.getName()))) {
                        bindingResult.reject("duplicate");
                        return Mono.just("pets/edit"); // TODO: Redirect to that pet
                    }

                    if (!bindingResult.hasErrors()) {
                        owner.getPets().add(pet);
                        return ownerRepository.save(owner).then(Mono.just("redirect:/" + OWNER_LIST));
                    } else {
                        model.addAttribute("isNew", true);
                        return Mono.just("pets/edit");
                    }
                });
    }

}
