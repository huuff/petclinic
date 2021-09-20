package xyz.haff.petclinic.models;

import xyz.haff.petclinic.util.Util;

public enum PetType {
    DOG, CAT;

    public String toString() {
        return Util.toCapitalized(name());
    }
}
