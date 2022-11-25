package com.wazv.sistemagestiontareas.services;

import com.wazv.sistemagestiontareas.models.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public interface UserService {
    
    List<User> getAll();

    Page<User> getAllPerPage(Pageable pageable);

    Optional<User> getById(String id);
    
    User save(User user);
    
    Boolean delete(String id);

    User getByAuth0(String auth0);

    User createUser(Map<String, Object> userData);
}
