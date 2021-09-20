package xyz.haff.petclinic.util;

import lombok.experimental.UtilityClass;
import org.thymeleaf.util.StringUtils;

@UtilityClass
public class Util {

    public static String toCapitalized(String input) {
        return StringUtils.capitalize(input.toLowerCase());
    }
}
