package com.wazv.sistemagestiontareas.servicesImpl;

import com.wazv.sistemagestiontareas.models.entities.Task;
import com.wazv.sistemagestiontareas.models.entities.User;
import com.wazv.sistemagestiontareas.repositories.TaskRepository;
import com.wazv.sistemagestiontareas.services.TaskService;
import com.wazv.sistemagestiontareas.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserService userService;

    public List<Task> getAll() {
        return taskRepository.findAll();
    };

    public Page<Task> getAllPerPage(Pageable pageable) {
        return taskRepository.findAll(pageable);
    };

    public Task getById(String id) {
        return taskRepository.findById(id).get();
    }

    @Override
    public Task save(Task task) {
        task.setUpdatedAt(new Date());
        return taskRepository.save(task);
    }
    
    public Boolean delete(String id) {
        Boolean hadDeleted = !getById(id).getId().isEmpty();
        taskRepository.deleteById(id);
        return hadDeleted;
    }

    @Override
    public void create(Task task, String userId) {
        User user = userService.getByIdAuth0(userId);
        task.setUser(user);
        save(task);
    }

    @Override
    public List<Task> getAllByIdUsuario(String idUsuario) {
        return taskRepository.findAllByUserId(idUsuario);
    }
}
