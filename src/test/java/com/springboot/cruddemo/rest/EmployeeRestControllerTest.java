package com.springboot.cruddemo.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.cruddemo.dto.EmployeeDTO;
import com.springboot.cruddemo.entity.Employee;
import com.springboot.cruddemo.service.EmployeeService;
import org.junit.jupiter.api.Assertions;
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
    void givenListOfEmployees_willReturnEmployeesList() throws Exception {
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
    void givenEmployeeId_willReturnEmployeeObject_True() throws Exception{
        // given
        int id = 1;
        Employee employee = Employee.builder()
                .id(id)
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
    void givenEmployeeId_willReturnEmployeeObject_False() throws Exception{

        // given
        given(employeeService.findById(1)).willReturn(null).willThrow(DataNotFoundException.class);

        mockMvc.perform(get("/api/employees/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }

    // save()
    @Test
    void givenEmployeeObject_writeEmployeeObject() throws Exception{
        // given
        EmployeeDTO employee = EmployeeDTO.builder()
                .firstName("Harry")
                .lastName("Potter")
                .email("harry@gmail.com")
                .build();

        when(employeeService.save(employee)).thenReturn(employee);

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

    @Test
    void saveEmployeeTest() {
        EmployeeDTO employee = EmployeeDTO.builder()
                .id(1)
                .firstName("Harry")
                .lastName("Potter")
                .email("harry@gmail.com")
                .build();

        when(employeeService.save(employee)).thenReturn(employee);
        Assertions.assertEquals(employee, employeeService.save(employee));
    }

     // update()
    @Test
    void givenEmployeeObject_writeUpdatedEmployeeObject_True() throws Exception{
        // given
        EmployeeDTO employee = EmployeeDTO.builder()
                .firstName("Harry")
                .lastName("Potter")
                .email("harry@gmail.com")
                .build();

        when(employeeService.save(employee)).thenReturn(employee);

        // when
        ResultActions response = mockMvc.perform(put("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)));
        // then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.firstName", is(employee.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(employee.getLastName())))
                .andExpect(jsonPath("$.email", is(employee.getEmail())));
    }

    @Test
    void givenEmployeeObject_writeUpdatedEmployeeObject_False() throws Exception{
        // given
        EmployeeDTO employee = EmployeeDTO.builder()
                .firstName("Harry")
                .lastName("Potter")
                .email("harry@gmail.com")
                .build();

        when(employeeService.save(employee)).thenThrow(IllegalArgumentException.class);

        // when
        ResultActions response = mockMvc.perform(put("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)));
        // then
        response.andExpect(status().isBadRequest())
                .andDo(print());
    }

     // deleteById()
    @Test
    void givenEmployeeId_willDeleteEmployeeObject_True() throws Exception{
        // given
        Employee employee = Employee.builder()
                .id(1)
                .firstName("Harry")
                .lastName("Potter")
                .email("harry@gmail.com")
                .build();

        employeeService.deleteById(employee.getId());

        ResultActions response = mockMvc.perform(delete("/api/employees/{id}", employee.getId()));

        // then
        response.andExpect(status().isNotFound())
                .andDo(print());

    }

    // deleteById()
    @Test
    void givenEmployeeId_willDeleteEmployeeObject_False() throws Exception{
        // given
        int id = 1;
        willThrow(DataNotFoundException.class).given(employeeService).deleteById(id);

        // when
        ResultActions response = mockMvc.perform(delete("/api/employees/{id}", id));

        // then
        response.andExpect(status().isNotFound());
    }
}