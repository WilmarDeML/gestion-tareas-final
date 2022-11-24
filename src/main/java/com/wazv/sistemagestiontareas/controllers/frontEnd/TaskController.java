package com.wazv.sistemagestiontareas.controllers.frontEnd;

import com.wazv.sistemagestiontareas.models.entities.Task;
import com.wazv.sistemagestiontareas.models.enums.EnumState;
import com.wazv.sistemagestiontareas.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("tasks")
@Controller
public class TaskController {

    public String TITULO_TASKS = "Gestión de tareas";

    @Autowired
    TaskService taskService;

    @GetMapping("{idUsuario}")
    public String viewHome(Model model, @PathVariable String idUsuario) {
        model.addAttribute("tasks", taskService.getAllByIdUsuario(idUsuario));
        model.addAttribute("title", "Tareas");
        model.addAttribute("idUsuario", idUsuario);
        return "tasks";
    }

    /*@GetMapping("{email}")
    public String viewDetalleTarea(@PathVariable String email, Model model) {
        model.addAttribute("task", taskService.findByEmail(email));
        return "detalle-task";
    }*/

    @GetMapping("{idUsuario}/nueva")
    public String getTareaNuevo(Model model, @ModelAttribute("task") Task task, @PathVariable String idUsuario){
        model.addAttribute("title", "Nueva tarea");
        model.addAttribute("tituloPrincipal", TITULO_TASKS);
        model.addAttribute("accion", "Crear tarea");
        model.addAttribute("states", EnumState.values());
        model.addAttribute("method", "POST");
        model.addAttribute("idUsuario", idUsuario);
        return "new-task";
    }

    @PostMapping("{idUsuario}/nueva")
    public String postTarea(Model model, @ModelAttribute("task") Task task, @PathVariable String idUsuario){
        try {
            taskService.create(task, idUsuario);
            return "redirect:/tasks/".concat(idUsuario);
        } catch (DataAccessException e) {
            List<String> partsOfError = List.of(e.getMostSpecificCause().getLocalizedMessage().split(":"));
            String detail = partsOfError.get(2);
            model.addAttribute("error", detail);
            return "error";
        } catch (Exception e) {
            model.addAttribute("error", e.getLocalizedMessage());
            return "error";
        }
    }

    @DeleteMapping("{idUsuario}/{id}")
    public String delete(@PathVariable String id, @PathVariable String idUsuario){
        taskService.delete(id);
        return "redirect:/tasks/".concat(idUsuario);
    }

    @GetMapping("{idUsuario}/editar/{id}")
    public String update(Model model, @PathVariable String id, @PathVariable String idUsuario){
        try {
            Task task = taskService.getById(id);
            model.addAttribute("task", task);
            model.addAttribute("title", "Editar task");
            model.addAttribute("tituloPrincipal", TITULO_TASKS);
            model.addAttribute("accion", "Actualizar task");
            model.addAttribute("states", EnumState.values());
            model.addAttribute("method", "PATCH");
            model.addAttribute("idUsuario", idUsuario);

            return "new-task";
        } catch (Exception e) {
            return "redirect:/error";
        }
    }

    @PatchMapping("{idUsuario}/editar/{id}")
    public String update(@ModelAttribute("updateTarea") Task task, @PathVariable String id, @PathVariable String idUsuario){
        try {
            Task taskDb = taskService.getById(id);
            task.setCreatedAt(taskDb.getCreatedAt());
            taskService.create(task, idUsuario);
            return "redirect:/tasks/".concat(idUsuario);
        } catch (Exception e) {
            return "redirect:/error";
        }

    }
}
