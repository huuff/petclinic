package xyz.haff.petclinic.controllers;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ControllerUtil {

    public static String redirect(String url) {
        return "redirect:" + url;
    }
}
