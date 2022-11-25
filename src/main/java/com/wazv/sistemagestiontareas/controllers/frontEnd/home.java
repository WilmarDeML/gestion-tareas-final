package com.wazv.sistemagestiontareas.controllers.frontEnd;

import com.wazv.sistemagestiontareas.models.entities.User;
import com.wazv.sistemagestiontareas.services.TaskService;
import com.wazv.sistemagestiontareas.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class home {

    @Autowired
    private TaskService taskService;

    @Autowired
    UserService userService;

    @GetMapping("home/{idUsuario}")
    public String index(Model model, @PathVariable String idUsuario) {
        try {
            User usuario = userService.getByAuth0(idUsuario);
            model.addAttribute("name", usuario.getEmail());
            model.addAttribute("idUsuario", idUsuario);
            model.addAttribute("urlImagen", usuario.getImage());
            model.addAttribute("title", "Sistema-gestión-tareas");
            return "index";
        } catch (Exception e) {
            model.addAttribute("mensaje", e.getMessage() != null ? e.getMessage() : "Intente más tarde Ocurrió un error ".concat(e.getLocalizedMessage()));
            return "error";
        }
    }
}
