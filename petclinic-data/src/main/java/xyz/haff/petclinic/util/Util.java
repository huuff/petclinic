package xyz.haff.petclinic.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Util {

    public static String toCapitalized(String input) {
        return input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
    }
}
