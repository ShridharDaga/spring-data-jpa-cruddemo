package com.springboot.cruddemo.service;

import com.springboot.cruddemo.dto.ManagerDTO;
import com.springboot.cruddemo.entity.Manager;


import java.util.List;

public interface ManagerService {
    List<Manager> findAll();

    Manager findById(int id);

    ManagerDTO save(ManagerDTO managerDTO);

    void deleteById(int id);
}
