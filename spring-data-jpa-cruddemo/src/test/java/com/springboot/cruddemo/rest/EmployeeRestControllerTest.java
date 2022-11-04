package com.springboot.cruddemo.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.cruddemo.entity.Employee;
import com.springboot.cruddemo.service.EmployeeService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class EmployeeRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper;

    // findAll()
    @Test
    public void givenListOfEmployees_willReturnEmployeesList() throws Exception {
        // given (setup)
        List<Employee> employees = new ArrayList<>();

        employees.add(Employee.builder().firstName("Harry").lastName("Potter").email("harry@gmail.com").build());
        employees.add(Employee.builder().firstName("Tony").lastName("Stark").email("tony@gmail.com").build());

        given(employeeService.findAll()).willReturn(employees);

        // when
        ResultActions response = mockMvc.perform(get("/api/employees"));

        // then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()", is(employees.size())));
    }

    // findById()
    @Test
    public void givenEmployeeId_willReturnEmployeeObject_True() throws Exception{
        // given
        int id = 1;
        Employee employee = Employee.builder()
                .firstName("Harry")
                .lastName("Potter")
                .email("harry@gmail.com")
                .build();

        given(employeeService.findById(id)).willReturn(employee);

        // when
        ResultActions response = mockMvc.perform(get("/api/employees/{id}", id));

        // then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.firstName", is(employee.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(employee.getLastName())))
                .andExpect(jsonPath("$.email", is(employee.getEmail())));
    }

    @Test
    public void givenEmployeeId_willReturnEmployeeObject_wllThrowException() throws Exception{
        // given
        int id = 1;
        Employee employee = Employee.builder()
                .firstName("Harry")
                .lastName("Potter")
                .email("harry@gmail.com")
                .build();

//        given(employeeService.findById(id)).willThrow(RuntimeException.class);
        given(employeeService.findById(id)).willReturn(null);

        // when
        ResultActions response = mockMvc.perform(get("/api/employees/{id}", id));

        // then
        response.andExpect(status().isOk()).andDo(print());
    }

    // save()
    @Test
    public void givenEmployeeObject_writeEmployeeObject() throws Exception{
        // given
        Employee employee = Employee.builder()
                .firstName("Harry")
                .lastName("Potter")
                .email("harry@gmail.com")
                .build();

        willDoNothing().given(employeeService).save(employee);

        // when
        ResultActions response = mockMvc.perform(post("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)));
        // then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.firstName", is(employee.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(employee.getLastName())))
                .andExpect(jsonPath("$.email", is(employee.getEmail())));
    }

    // update()
    @Test
    public void givenEmployeeObject_writeUpdatedEmployeeObject() throws Exception{
        // given
        Employee employee = Employee.builder()
                .firstName("Harry")
                .lastName("Potter")
                .email("harry@gmail.com")
                .build();

        Employee updatedEmployee = Employee.builder()
                .firstName("Harry")
                .lastName("Styles")
                .email("harry@yahoo.com")
                .build();

        willDoNothing().given(employeeService).save(employee);

        // when
        ResultActions response = mockMvc.perform(post("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedEmployee)));
        // then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.firstName", is(updatedEmployee.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(updatedEmployee.getLastName())))
                .andExpect(jsonPath("$.email", is(updatedEmployee.getEmail())));
    }

    // deleteById()
    @Test
    public void givenEmployeeId_willDeleteEmployeeObject() throws Exception{
        // given
        int id = 1;
        willDoNothing().given(employeeService).deleteById(id);

        // when
        ResultActions response = mockMvc.perform(get("/api/employees/{id}", id));

        // then
        response.andExpect(status().isOk());
    }
}