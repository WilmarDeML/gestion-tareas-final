package com.wazv.sistemagestiontareas.repositories;

import com.wazv.sistemagestiontareas.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    User getByIdAuth0(String idAuth0);
}
