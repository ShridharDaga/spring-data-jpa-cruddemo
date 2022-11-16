package com.springboot.cruddemo.rest;

import com.springboot.cruddemo.dto.DepartmentDTO;
import com.springboot.cruddemo.entity.Department;
import com.springboot.cruddemo.service.DepartmentService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
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
class DepartmentRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DepartmentService departmentService;

    @Autowired
    private ObjectMapper objectMapper;

    // findAll()
    @Test
    void givenListOfDepartments_willReturnDepartmentList() throws Exception {
        // given (setup)
        List<Department> departments = new ArrayList<>();

        departments.add(Department.builder().id(1).name("HR").build());
        departments.add(Department.builder().id(1).name("Sales").build());

        given(departmentService.findAll()).willReturn(departments);

        // when
        ResultActions response = mockMvc.perform(get("/api/departments"));

        // then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()", is(departments.size())));
    }

    // findById()
    @Test
    void givenDepartmentId_willReturnDepartmentObject_True() throws Exception{
        // given
        int id = 1;
        Department department = Department.builder()
                .id(id)
                .name("HR")
                .build();

        given(departmentService.findById(id)).willReturn(department);

        // when
        ResultActions response = mockMvc.perform(get("/api/departments/{id}", id));

        // then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.name", is(department.getName())));
    }

    @Test
    void givenDepartmentId_willReturnDepartmentObject_False() throws Exception{

        // given
        given(departmentService.findById(1)).willReturn(null).willThrow(DataNotFoundException.class);

        mockMvc.perform(get("/api/departments/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }

    // save()
    @Test
    void givenDepartmentObject_writeDepartmentObject() throws Exception{
        // given
        DepartmentDTO department = DepartmentDTO.builder()
                .id(1)
                .name("HR")
                .build();

        when(departmentService.save(department)).thenReturn(department);

        // when
        ResultActions response = mockMvc.perform(post("/api/departments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(department)));
        // then
        response.andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void saveDepartmentTest() {
        DepartmentDTO department = DepartmentDTO.builder()
                .id(1)
                .name("HR")
                .build();

        when(departmentService.save(department)).thenReturn(department);
        Assertions.assertEquals(department, departmentService.save(department));
    }

    // update()
    @Test
    void givenDepartmentObject_writeUpdatedDepartmentObject_True() throws Exception{
        // given
        DepartmentDTO department = DepartmentDTO.builder()
                .id(1)
                .name("HR")
                .build();

        when(departmentService.save(department)).thenReturn(department);

        // when
        ResultActions response = mockMvc.perform(put("/api/departments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(department)));
        // then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.name", is(department.getName())));
    }

    @Test
    void givenDepartmentObject_writeUpdatedDepartmentObject_False() throws Exception{
        // given
        DepartmentDTO department = DepartmentDTO.builder()
                .id(1)
                .name("HR")
                .build();

        when(departmentService.save(department)).thenThrow(IllegalArgumentException.class);

        // when
        ResultActions response = mockMvc.perform(put("/api/departments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(department)));
        // then
        response.andExpect(status().isBadRequest())
                .andDo(print());
    }

    // deleteById()
    @Test
    void givenDepartmentId_willDeleteDepartmentObject_True() throws Exception{
        // given
        Department department = Department.builder()
                .id(1)
                .name("HR")
                .build();

        departmentService.deleteById(department.getId());

        ResultActions response = mockMvc.perform(delete("/api/departments/{id}", department.getId()));

        // then
        response.andExpect(status().isNotFound())
                .andDo(print());

    }

    // deleteById()
    @Test
    void givenDepartmentId_willDeleteDepartmentObject_False() throws Exception{
        // given
        int id = 1;
        willThrow(DataNotFoundException.class).given(departmentService).deleteById(id);

        // when
        ResultActions response = mockMvc.perform(delete("/api/departments/{id}", id));

        // then
        response.andExpect(status().isNotFound());
    }
}