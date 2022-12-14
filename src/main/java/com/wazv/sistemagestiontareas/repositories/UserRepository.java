package com.wazv.sistemagestiontareas.repositories;

import com.wazv.sistemagestiontareas.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    User getByAuth0(String auth0);
}
