package com.springboot.cruddemo.service;

import com.springboot.cruddemo.converter.ManagerConverter;
import com.springboot.cruddemo.dao.ManagerRepository;
import com.springboot.cruddemo.dto.ManagerDTO;
import com.springboot.cruddemo.entity.Manager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ManagerServiceImpl implements ManagerService{
    private final ManagerRepository managerRepository;

    @Autowired
    ManagerConverter managerConverter;


    @Autowired
    public ManagerServiceImpl(ManagerRepository managerRepository) {
        this.managerRepository = managerRepository;
    }

    @Override
    public List<Manager> findAll() {
        return managerRepository.findAll();
    }

    @Override
    public Manager findById(int id) {
        Optional<Manager> result = managerRepository.findById(id);

        Manager manager;

        if(result.isPresent())
            manager = result.get();
        else
            throw new IllegalArgumentException("Did not find manager id = " + id);

        return manager;
    }

    @Override
    public ManagerDTO save(ManagerDTO managerDTO) {
        Manager manager = managerConverter.convertDtoToEntity(managerDTO);
        managerRepository.save(manager);
        ManagerDTO temp = managerConverter.convertEntityToDto(manager);
        return temp;
    }

    @Override
    public void deleteById(int id) {
        managerRepository.deleteById(id);
    }
}
