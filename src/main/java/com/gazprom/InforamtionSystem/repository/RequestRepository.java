package com.gazprom.InforamtionSystem.repository;

import com.gazprom.InforamtionSystem.model.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {
    Request getById(Long id);
    List<Request> getAllByStatus(String status);
}
