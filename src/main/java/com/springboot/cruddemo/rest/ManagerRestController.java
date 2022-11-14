package com.springboot.cruddemo.rest;

import com.springboot.cruddemo.dto.ManagerDTO;
import com.springboot.cruddemo.entity.Manager;
import com.springboot.cruddemo.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ManagerRestController {

    private final ManagerService managerService;

    @Autowired
    public ManagerRestController(ManagerService managerService) {
        this.managerService = managerService;
    }

    @GetMapping("/managers")
    public List<Manager> findAll(){
        return managerService.findAll();
    }

    @GetMapping("/managers/{mgId}")
    public Manager getManager(@PathVariable int mgId){
        Manager manager = managerService.findById(mgId);

        if(manager == null)
            throw new DataNotFoundException("Manager ID not found : " + mgId);

        return manager;
    }

    @PostMapping(value = "/managers")
    @ResponseBody
    public ManagerDTO addManager(@RequestBody ManagerDTO managerDTO, BindingResult result){
        if(result.hasErrors()){
            throw new IllegalArgumentException("Enter valid record!");
        }
        return managerService.save(managerDTO);
    }

    @PutMapping(value = "/managers")
    @ResponseBody
    public ManagerDTO updateManager(@RequestBody ManagerDTO managerDTO, BindingResult result){
        if(result.hasErrors())
            throw new IllegalArgumentException("Enter valid record!");
        return managerService.save(managerDTO);
    }

    @DeleteMapping("/managers/{mgId}")
    public String deleteManager(@PathVariable int mgId){
        Manager manager = managerService.findById(mgId);
        if(manager == null)
            throw new DataNotFoundException("Manager ID not found : " + mgId);

        managerService.deleteById(mgId);
        return "Deleted Manager = " + mgId;
    }
}
