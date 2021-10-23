package xyz.haff.petclinic.models;

public enum PetType implements Named{
    CAT,
    DOG,
    BIRD,
    LIZARD,
    TURTLE
  ;

    @Override
    public String prettyName() {
        return this.name().toLowerCase();
    }
}
