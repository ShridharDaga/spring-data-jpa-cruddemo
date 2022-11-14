package com.springboot.cruddemo.dao;

import com.springboot.cruddemo.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {
    // that's it...
}
