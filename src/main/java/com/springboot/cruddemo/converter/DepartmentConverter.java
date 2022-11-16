package com.springboot.cruddemo.converter;

import com.springboot.cruddemo.dto.DepartmentDTO;
import com.springboot.cruddemo.entity.Department;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class DepartmentConverter {

    public DepartmentDTO convertEntityToDto(Department department) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(department, DepartmentDTO.class);
    }

    public Department convertDtoToEntity(DepartmentDTO departmentDTO) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(departmentDTO, Department.class);
    }
}
