package xyz.haff.petclinic.models;

public enum Specialty {
    OPHTHALMOLOGY,
    SURGERY,
    DERMATOLOGY,
    DENTISTRY,
    RADIOLOGY
    ;

    /**
     * Get the name as capitalized
     * @return the capitalized name
     */
    public String toString() {
        return name().substring(0, 1).toUpperCase() + name().substring(1).toLowerCase();
    }
}
