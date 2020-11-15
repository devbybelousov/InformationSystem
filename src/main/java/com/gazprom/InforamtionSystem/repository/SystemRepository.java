package com.gazprom.InforamtionSystem.repository;

import com.gazprom.InforamtionSystem.model.InformationSystem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SystemRepository extends JpaRepository<InformationSystem, Long> {
    InformationSystem findByTitle(String title);
}
