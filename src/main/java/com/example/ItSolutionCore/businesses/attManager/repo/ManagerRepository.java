package com.example.ItSolutionCore.businesses.attManager.repo;

import com.example.ItSolutionCore.businesses.attManager.entity.user.manager.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Long> {
}
