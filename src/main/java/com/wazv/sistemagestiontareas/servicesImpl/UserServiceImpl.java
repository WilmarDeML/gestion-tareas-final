package com.wazv.sistemagestiontareas.servicesImpl;

import com.wazv.sistemagestiontareas.models.entities.User;
import com.wazv.sistemagestiontareas.repositories.UserRepository;
import com.wazv.sistemagestiontareas.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAll() {
        return userRepository.findAll();
    };

    public Page<User> getAllPerPage(Pageable pageable) {
        return userRepository.findAll(pageable);
    };

    public Optional<User> getById(String id) {
        return userRepository.findById(id);
    };
    
    public User save(User user) {
        return userRepository.save(user);
    };
    
    public Boolean delete(String id) {
        Boolean hadDeleted = getByAuth0(id) != null;
        userRepository.deleteById(id);
        return hadDeleted;
    }

    @Override
    public User getByAuth0(String auth0) {
        return userRepository.getByAuth0(auth0);
    }

    @Override
    public User createUser(Map<String, Object> userData) {
        User user = new User();
        user.setAuth0(userData.get("sub").toString().substring(6));
        user.setEmail(userData.get("email").toString());
        user.setImage(userData.get("picture").toString());
        user.setUpdatedAt(new Date());
        return save(user);
    };
}
