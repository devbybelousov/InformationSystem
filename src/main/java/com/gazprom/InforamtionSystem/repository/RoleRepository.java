package com.gazprom.InforamtionSystem.repository;

import com.gazprom.InforamtionSystem.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRole(String role);
    Optional<Role> findById(int id);

}
