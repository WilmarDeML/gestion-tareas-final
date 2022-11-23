package com.wazv.sistemagestiontareas.controllers.backEnd;

import com.wazv.sistemagestiontareas.models.entities.Task;
import com.wazv.sistemagestiontareas.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("api/tasks")
public class TaskRestController {
    @Autowired
    private TaskService taskService;

    @GetMapping
    public List<Task> listAll() {
        return taskService.getAll();
    }

    @GetMapping("page/{page}")
    public Page<Task> listAll(@PathVariable Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        return taskService.getAllPerPage(pageable);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> listById(@PathVariable String id) {
        Map<String, Object> response = new HashMap<>();
        try {
            Task task = taskService.getById(id);
            if (task == null) {
                response.put("mensaje", "La tarea con id ".concat(id.toString()).concat(" no existe en base de datos"));
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(task, HttpStatus.OK);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar la consulta en base de datos");
            response.put("error", Objects.requireNonNull(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Task task) {
        Map<String, Object> response = new HashMap<>();
        try {
            task = taskService.save(task);
            response.put("mensaje", "La tarea ha sido creada con éxito!");
            response.put("tarea", task);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar el insert en base de datos");
            response.put("error", Objects.requireNonNull(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("{id}")
    public ResponseEntity<?> update(@RequestBody Task task, @PathVariable String id) {
        Map<String, Object> response = new HashMap<>();
        try {
            task = taskService.getById(id);
            if (task == null) {
                response.put("mensaje", "La tarea que desea editar, con id ".concat(id.toString().concat(" no existe en base de datos!")));
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
            response.put("mensaje", "La tarea ha sido actualizado con éxito!");
            response.put("tarea", task);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al actaulizar tarea en base de datos");
            response.put("error", Objects.requireNonNull(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        Map<String, Object> response = new HashMap<>();
        try {
            if (!taskService.delete(id)) {
                response.put("mensaje", "La tarea que desea eliminar, con id ".concat(id.toString().concat(" no existe en base de datos!")));
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
            response.put("mensaje", "La tarea ha sido eliminada con éxito!");
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al eliminar tarea en base de datos");
            response.put("error", Objects.requireNonNull(e.getMessage()).concat(": ".concat(e.getMostSpecificCause().getMessage())));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
