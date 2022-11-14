package com.springboot.cruddemo.converter;

import com.springboot.cruddemo.dto.ManagerDTO;
import com.springboot.cruddemo.entity.Manager;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ManagerConverter {

    public ManagerDTO convertEntityToDto(Manager manager) {
        ModelMapper modelMapper = new ModelMapper();
        System.out.println("converter : " + manager);
        ManagerDTO temp = modelMapper.map(manager, ManagerDTO.class);
        System.out.println("-----" + temp);
        return temp;
    }

    public Manager convertDtoToEntity(ManagerDTO managerDTO) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(managerDTO, Manager.class);
    }
}
