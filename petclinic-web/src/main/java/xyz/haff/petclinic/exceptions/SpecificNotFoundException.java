package xyz.haff.petclinic.exceptions;

import lombok.Getter;

import java.util.UUID;

@Getter
public class SpecificNotFoundException extends RuntimeException {
    private final String type;
    private final String object;

    public SpecificNotFoundException(String type, String object) {
        this.type = type;
        this.object = object;
    }

    public static SpecificNotFoundException fromOwnerId(UUID ownerId) {
        return new SpecificNotFoundException("owner_not_found", ownerId.toString());
    }

    public static SpecificNotFoundException fromUserId(UUID userId) {
        return new SpecificNotFoundException("user_not_found", userId.toString());
    }

    public static SpecificNotFoundException fromVetId(UUID vetId) {
        return new SpecificNotFoundException("vet_not_found", vetId.toString());
    }

    public static SpecificNotFoundException fromPetId(UUID petId) {
        return new SpecificNotFoundException("pet_not_found", petId.toString());
    }
}
