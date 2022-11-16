package com.springboot.cruddemo.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.cruddemo.dao.EmployeeRepository;
import com.springboot.cruddemo.entity.Employee;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class EmployeeServiceImplTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeRepository employeeRepository;


    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void deleteMethodShouldOnce(){
        Employee employee = Employee.builder()
                .id(1)
                .firstName("Harry")
                .lastName("Potter")
                .email("harry@gmail.com")
                .build();
        employeeRepository.deleteById(employee.getId());
        then(employeeRepository).should(times(1)).deleteById(1);
    }

    // findAll()
    @Test
    void givenListOfEmployees_willReturnEmployeesList() throws Exception {
        // given (setup)
        List<Employee> employees = new ArrayList<>();

        employees.add(Employee.builder().firstName("Harry").lastName("Potter").email("harry@gmail.com").build());
        employees.add(Employee.builder().firstName("Tony").lastName("Stark").email("tony@gmail.com").build());

        given(employeeRepository.findAll()).willReturn(employees);

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

        given(employeeRepository.findById(id)).willReturn(Optional.ofNullable(employee));

        // when
        ResultActions response = mockMvc.perform(get("/api/employees/{id}", id));

        // then
        assert employee != null;
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.firstName", is(employee.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(employee.getLastName())))
                .andExpect(jsonPath("$.email", is(employee.getEmail())));
    }

    @Test
    void givenEmployeeId_willReturnEmployeeObject_False() throws Exception{

        // given
        given(employeeRepository.findById(1)).willThrow(IllegalArgumentException.class);

        mockMvc.perform(get("/api/employees/{id}", "null")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

    // save()
    @Test
    void givenEmployeeObject_writeEmployeeObject() throws Exception{
        // given
        Employee employee = Employee.builder()
                .firstName("Harry")
                .lastName("Potter")
                .email("harry@gmail.com")
                .build();

        when(employeeRepository.save(employee)).thenReturn(employee);

        // when
        ResultActions response = mockMvc.perform(post("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)));
        // then
        response.andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    void saveEmployeeTest() {
        Employee employee = Employee.builder()
                .id(1)
                .firstName("Harry")
                .lastName("Potter")
                .email("harry@gmail.com")
                .build();

        when(employeeRepository.save(employee)).thenReturn(employee);
        Assertions.assertEquals(employee, employeeRepository.save(employee));
    }

    // update()
    @Test
    void givenEmployeeObject_writeUpdatedEmployeeObject() throws Exception{
        // given
        Employee employee = Employee.builder()
                .firstName("Harry")
                .lastName("Potter")
                .email("harry@gmail.com")
                .build();

        when(employeeRepository.save(employee)).thenReturn(employee);

        // when
        ResultActions response = mockMvc.perform(put("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)));
        // then
        response.andExpect(status().isBadRequest())
                .andDo(print());
    }

    // deleteById()
//    @Test
//    void givenEmployeeId_willDeleteEmployeeObject_True() throws Exception{
//        // given
//        Employee employee = Employee.builder()
//                .id(1)
//                .firstName("Harry")
//                .lastName("Potter")
//                .email("harry@gmail.com")
//                .build();
//
//        employeeRepository.deleteById(employee.getId());
//
//        ResultActions response = mockMvc.perform(delete("/api/employees/{id}", employee.getId()));
//
//        // then
//        response.andExpect(status().isBadRequest())
//                .andDo(print());
//
//    }

    // deleteById()
    @Test
    void givenEmployeeId_willDeleteEmployeeObject_False() throws Exception{
        // given
        int id = 1;
        willDoNothing().given(employeeRepository).deleteById(id);

        // when
        ResultActions response = mockMvc.perform(delete("/api/employees/{id}", id));

        // then
        response.andExpect(status().isBadRequest());
    }
}