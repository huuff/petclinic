package xyz.haff.petclinic.services;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import xyz.haff.petclinic.models.PersonalData;
import xyz.haff.petclinic.models.Pet;

import java.util.Locale;

/**
 * Adds an appropriate title to the model
 */

@Service
@RequiredArgsConstructor
public class TitleService {
    private final MessageSource messageSource;

    public void listOwners(Model model, Locale locale) {
        addTitle(model, getTranslationForLocale("owners", locale));
    }

    public void listVets(Model model, Locale locale) {
        addTitle(model, getTranslationForLocale("vets", locale));
    }

    public void listPets(Model model, Locale locale) {
        addTitle(model, getTranslationForLocale("pets", locale));
    }

    public void newOwner(Model model, Locale locale) {
        addTitle(model, getTranslationForLocale("new_owner", locale));
    }

    public void newVet(Model model, Locale locale) {
        addTitle(model, getTranslationForLocale("new_vet", locale));
    }

    public void newPet(Model model, Locale locale) {
        addTitle(model, getTranslationForLocale("new_pet", locale));
    }

    public void person(Model model, PersonalData personalData) {
        addTitle(model, personalData.fullName());
    }

    public void pet(Model model, Pet pet) {
        addTitle(model, pet.getName());
    }

    public void welcomeAnon(Model model, Locale locale) {
        addTitle(model, getTranslationForLocale("welcome", locale));
    }

    public void welcomeAuth(Model model, Locale locale, PersonalData personalData) {
        addTitle(model, messageSource.getMessage("welcome_name", new Object[]{personalData.getFirstName()}, locale));
    }

    private void addTitle(Model model, String message) {
        model.addAttribute("title", message);
    }

    private String getTranslationForLocale(String code, Locale locale) {
        return messageSource.getMessage(code, new Object[]{}, locale);
    }
}
