package xyz.haff.petclinic.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import xyz.haff.petclinic.services.RegisterService;
import xyz.haff.petclinic.models.forms.OwnerForm;
import xyz.haff.petclinic.services.OwnerService;

// TODO: This is the exact same as OwnersController.create()

@Controller
@RequiredArgsConstructor
@RequestMapping(RegisterController.PATH)
public class RegisterController {
    public static final String PATH = "/register";
    private static final String TEMPLATE = "owners/edit";

    private final RegisterService registerService;
    private final OwnerService ownerService;


    @GetMapping
    public String showForm(Model model) {
        model.addAttribute("ownerForm", new OwnerForm());

        return TEMPLATE;
    }

    @PreAuthorize("permitAll()")
    @PostMapping
    public String register(@Validated(OwnerForm.NewOwnerConstraintGroup.class) @ModelAttribute OwnerForm ownerForm, BindingResult bindingResult) {
        if (!ownerService.checkNewIsValid(ownerForm, bindingResult))
            return TEMPLATE;

        registerService.login(registerService.registerOwner(ownerForm));

        return "redirect:/";
    }
}
