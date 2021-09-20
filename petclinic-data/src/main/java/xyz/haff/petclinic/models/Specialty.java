package xyz.haff.petclinic.models;

import xyz.haff.petclinic.util.Util;

public enum Specialty {
    DENTISTRY, SURGERY, RADIOLOGY, DERMATOLOGY, OPHTHALMOLOGY;

    public String toString() {
        return Util.toCapitalized(name());
    }
}
