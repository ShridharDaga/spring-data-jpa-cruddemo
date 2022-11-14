package com.springboot.cruddemo.rest;

import com.springboot.cruddemo.dto.DepartmentDTO;
import com.springboot.cruddemo.entity.Department;
import com.springboot.cruddemo.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class DepartmentRestController {

    private final DepartmentService departmentService;

    @Autowired
    public DepartmentRestController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("/departments")
    public List<Department> findAll(){
        return departmentService.findAll();
    }

    @GetMapping("/departments/{deptId}")
    public Department getDepartment(@PathVariable int deptId){
        Department department = departmentService.findById(deptId);
        if(department == null)
            throw new DataNotFoundException("Department ID not found : " + deptId);

        return department;
    }

    @PostMapping("/departments")
    public DepartmentDTO addDepartment(@RequestBody DepartmentDTO departmentDTO, BindingResult result){
        if(result.hasErrors())
            throw new IllegalArgumentException("Enter Valid record!");

        // also just in case they pass an id in JSON ... set id to 0
        // this is to force a save of new item ... instead of update
        departmentDTO.setId(0);

        return departmentService.save(departmentDTO);
    }

    /// DTO
    @PutMapping("/departments")
    public DepartmentDTO updateDepartment(@RequestBody DepartmentDTO departmentDTO, BindingResult result){
        if(result.hasErrors())
            throw new IllegalArgumentException("Enter Valid record!");
        return departmentService.save(departmentDTO);
    }

    @Modifying
    @DeleteMapping("/departments/{deptId}")
    public String deleteDepartment(@PathVariable int deptId){
        Department department = departmentService.findById(deptId);
        if(department == null)
            throw new DataNotFoundException("Department ID not found : " + deptId);

        departmentService.deleteById(deptId);
        return "Deleted Department = " + deptId;
    }
}
