package com.gazprom.InforamtionSystem.repository;

import com.gazprom.InforamtionSystem.model.Request;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RequestRepository extends JpaRepository<Long, Request> {
    List<Request> getAll();
}
