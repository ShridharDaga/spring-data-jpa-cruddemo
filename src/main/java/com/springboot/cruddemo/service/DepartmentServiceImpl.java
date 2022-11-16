package com.springboot.cruddemo.service;

import com.springboot.cruddemo.converter.DepartmentConverter;
import com.springboot.cruddemo.dao.DepartmentRepository;
import com.springboot.cruddemo.dto.DepartmentDTO;
import com.springboot.cruddemo.entity.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentServiceImpl implements DepartmentService{
    private final DepartmentRepository departmentRepository;

    @Autowired
    DepartmentConverter departmentConverter;

    @Autowired
    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public List<Department> findAll() {
        return departmentRepository.findAll();
    }

    @Override
    public Department findById(int id) {
        Optional<Department> result = departmentRepository.findById(id);

        Department department;

        if(result.isPresent())
            department = result.get();
        else
            throw new IllegalArgumentException("Did not find department id = " + id);

        return department;
    }

    @Override
    public DepartmentDTO save(DepartmentDTO departmentDTO) {
        Department department = departmentConverter.convertDtoToEntity(departmentDTO);
        departmentRepository.save(department);
        return departmentConverter.convertEntityToDto(department);
    }

    @Override
    public void deleteById(int id) {
        departmentRepository.deleteById(id);
    }
}
