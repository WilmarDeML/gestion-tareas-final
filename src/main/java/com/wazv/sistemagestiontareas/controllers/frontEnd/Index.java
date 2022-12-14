package com.wazv.sistemagestiontareas.controllers.frontEnd;

import com.wazv.sistemagestiontareas.models.entities.Task;
import com.wazv.sistemagestiontareas.models.entities.User;
import com.wazv.sistemagestiontareas.services.TaskService;
import com.wazv.sistemagestiontareas.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller
public class Index {

    @Autowired
    private TaskService taskService;

    @Autowired
    UserService userService;

    @GetMapping
    public String index(Model model, @AuthenticationPrincipal OidcUser principal) {
        String auth0 = "";
        model.addAttribute("title", "Sistema-gestión-tareas");
        try {
            if (principal != null) {
                auth0 = principal.getClaimAsString("sub").substring(6);
                User user = userService.getByAuth0(auth0);
                if ( user == null) {
                    user = userService.createUser(principal.getClaims());
                }
                model.addAttribute("idUsuario", auth0);
                model.addAttribute("name", user.getEmail());
                model.addAttribute("urlImagen", user.getImage());
                return "redirect:/home/".concat(auth0);
            }
            return "index";
        } catch (Exception e) {
            model.addAttribute("idUsuario", auth0);
            model.addAttribute("mensaje", e.getMessage() != null ? e.getMessage() : "Intente más tarde Ocurrió un error ".concat(e.getLocalizedMessage()));
            return "error";
        }
    }
}
