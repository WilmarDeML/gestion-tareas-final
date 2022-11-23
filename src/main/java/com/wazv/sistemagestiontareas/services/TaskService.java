package com.wazv.sistemagestiontareas.services;

import com.wazv.sistemagestiontareas.models.entities.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TaskService {
    
    List<Task> getAll();

    Page<Task> getAllPerPage(Pageable pageable);

    Task getById(String id);
    
    Task save(Task task);
    
    Boolean delete(String id);

    void create(Task task, String userId);

    List<Task> getAllById(String idUsuario);
}
