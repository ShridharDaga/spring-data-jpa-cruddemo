package com.springboot.cruddemo.rest;

import com.springboot.cruddemo.dto.EmployeeDTO;
import com.springboot.cruddemo.entity.Employee;
import com.springboot.cruddemo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EmployeeRestController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeRestController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employees")
    public List<Employee> findAll(){
        return employeeService.findAll();
    }

    @GetMapping("/employees/{empId}")
    public Employee getEmployee(@PathVariable int empId){
        Employee employee = employeeService.findById(empId);
            if(employee == null)
                throw new DataNotFoundException("Employee ID not found : " + empId);

        return employee;
    }

    @PostMapping("/employees")
    public EmployeeDTO addEmployee(@RequestBody EmployeeDTO employee, BindingResult result){
        if(result.hasErrors())
            throw new IllegalArgumentException("Enter Valid record!");

        // also just in case they pass an id in JSON ... set id to 0
        // this is to force a save of new item ... instead of update
        employee.setId(0);

        return employeeService.save(employee);
    }

    /// DTO
    @PutMapping("/employees")
    public EmployeeDTO updateEmployee(@RequestBody EmployeeDTO employee, BindingResult result){
        if(result.hasErrors())
            throw new IllegalArgumentException("Enter Valid record!");
        return employeeService.save(employee);
    }

    @DeleteMapping("/employees/{empId}")
    public String deleteEmployee(@PathVariable int empId){
        Employee employee = employeeService.findById(empId);
        if(employee == null)
            throw new DataNotFoundException("Employee ID not found : " + empId);

        employeeService.deleteById(empId);
        return "Deleted Employee = " + empId;
    }
}
