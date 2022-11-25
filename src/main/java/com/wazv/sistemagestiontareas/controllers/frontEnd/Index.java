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
        try {
            if (principal != null) {
                String idAuth0 = principal.getClaimAsString("sub").substring(6);
                User user = userService.getByIdAuth0(idAuth0);
                System.out.println("idAuth0: " + idAuth0 + "-----------------------------------------------" + user);
                if ( user == null) {
                    user = userService.createUser(principal.getClaims());
                }
                model.addAttribute("profile", principal.getClaims());
                model.addAttribute("idUsuario", idAuth0);
                /*model.addAttribute("name", user.getEmail());
                model.addAttribute("idUsuario", user.getIdAuth0());
                model.addAttribute("urlImagen", user.getImage());*/
            }
            model.addAttribute("title", "Sistema-gestión-tareas");
            return "index";
        } catch (Exception e) {
            return "index";
        }
    }
}
