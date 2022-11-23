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

import java.util.List;
import java.util.Optional;

@Controller
public class Index {

    @Autowired
    private TaskService taskService;

    @Autowired
    UserService userService;

    @GetMapping
    public String index(Model model, @AuthenticationPrincipal OidcUser principal) {
        if (principal != null) {
            String id = principal.getAttribute("sid");
            Optional<User> user = userService.getById(id);
            if ( user.isEmpty()) {
                userService.createUser(principal.getClaims());
            }
            model.addAttribute("name", principal.getAttribute("nickname"));
            model.addAttribute("idUsuario", id);
            model.addAttribute("urlImagen", principal.getAttribute("picture"));
        }
        model.addAttribute("title", "Landing");
        return "index";
    }
}
