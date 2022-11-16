package com.springboot.cruddemo.service;

import com.springboot.cruddemo.dto.DepartmentDTO;
import com.springboot.cruddemo.entity.Department;

import java.util.List;

public interface DepartmentService {
    List<Department> findAll();

    Department findById(int id);

    DepartmentDTO save(DepartmentDTO departmentDTO);

    void deleteById(int id);
}
