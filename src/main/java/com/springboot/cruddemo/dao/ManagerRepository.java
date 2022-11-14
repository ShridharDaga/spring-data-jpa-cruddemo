package com.springboot.cruddemo.dao;

import com.springboot.cruddemo.entity.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagerRepository extends JpaRepository<Manager, Integer> {
    // that's it...
}
