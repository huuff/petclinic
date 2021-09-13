package xyz.haff.petclinic.models;

import org.thymeleaf.util.StringUtils;

public enum PetType {
    DOG, CAT;

    public String toString() {
        return StringUtils.capitalize(name().toLowerCase());
    }
}
