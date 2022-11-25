package com.wazv.sistemagestiontareas.repositories;

import com.wazv.sistemagestiontareas.models.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, String> {
    List<Task> findAllByUserAuth0(String idUsuario);
}
