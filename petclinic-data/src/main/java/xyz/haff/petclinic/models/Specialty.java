package xyz.haff.petclinic.models;

public enum Specialty {
    OPHTHALMOLOGY,
    SURGERY,
    DERMATOLOGY,
    DENTISTRY,
    RADIOLOGY
    ;

    // Just because I wanted my properties in lowercase
    public String toMessageProperty() {
        return name().toLowerCase();
    }
}
