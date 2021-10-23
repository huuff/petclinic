package xyz.haff.petclinic.models;

public enum Specialty implements Named {
    OPHTHALMOLOGY,
    SURGERY,
    DERMATOLOGY,
    DENTISTRY,
    RADIOLOGY
    ;

    @Override
    public String prettyName() {
        return name().toLowerCase();
    }
}
