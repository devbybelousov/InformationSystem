package com.gazprom.InforamtionSystem.repository;

import com.gazprom.InforamtionSystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(Long id);
    Optional<User> findByUserName(String userName);
    Boolean existsByUserName(String userName);
}
