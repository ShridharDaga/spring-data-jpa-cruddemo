package com.springboot.cruddemo.service;

import com.springboot.cruddemo.dto.EmployeeDTO;
import com.springboot.cruddemo.entity.Employee;

import java.util.List;

public interface EmployeeService {
    List<Employee> findAll();

    Employee findById(int id);

    EmployeeDTO save(EmployeeDTO employeeDTO);

    void deleteById(int id);
}
