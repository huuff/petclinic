package xyz.haff.petclinic.exceptions;

import lombok.Getter;

@Getter
public class SpecificNotFoundException extends RuntimeException {
    private final String type;
    private final String object;

    public SpecificNotFoundException(String type, String object) {
        this.type = type;
        this.object = object;
    }
}
