package com.springboot.cruddemo.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.cruddemo.dao.DepartmentRepository;
import com.springboot.cruddemo.entity.Department;
import com.springboot.cruddemo.rest.DataNotFoundException;
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
class DepartmentServiceImplTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DepartmentRepository departmentRepository;


    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void deleteMethodShouldOnce(){
        Department department = Department.builder()
                .id(1)
                .name("HR")
                .build();
        departmentRepository.deleteById(department.getId());
        then(departmentRepository).should(times(1)).deleteById(1);
    }

    // findAll()
    @Test
    void givenListOfDepartments_willReturnDepartmentsList() throws Exception {
        // given (setup)
        List<Department> departments = new ArrayList<>();

        departments.add(Department.builder().id(1).name("HR").build());
        departments.add(Department.builder().id(1).name("Sales").build());

        given(departmentRepository.findAll()).willReturn(departments);

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

        given(departmentRepository.findById(id)).willReturn(Optional.ofNullable(department));

        // when
        ResultActions response = mockMvc.perform(get("/api/departments/{id}", id));

        // then
        assert department != null;
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.name", is(department.getName())));
    }

    @Test
    void givenDepartmentId_willReturnDepartmentObject_False() throws Exception{

        // given
        given(departmentRepository.findById(1)).willReturn(null);
        mockMvc.perform(get("/api/departments/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andDo(print());

    }

    // save()
    @Test
    void givenDepartmentObject_writeDepartmentObject() throws Exception{
        // given
        Department department = Department.builder()
                .name("HR")
                .build();

        when(departmentRepository.save(department)).thenReturn(department);

        // when
        ResultActions response = mockMvc.perform(post("/api/departments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(department)));
        // then
        response.andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    void saveDepartmentTest() {
        Department department = Department.builder()
                .id(1)
                .name("HR")
                .build();

        when(departmentRepository.save(department)).thenReturn(department);
        Assertions.assertEquals(department, departmentRepository.save(department));
    }

    // update()
    @Test
    void givenDepartmentObject_writeUpdatedDepartmentObject() throws Exception{
        // given
        Department department = Department.builder()
                .name("HR")
                .build();

        when(departmentRepository.save(department)).thenReturn(department);

        // when
        ResultActions response = mockMvc.perform(put("/api/departments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(department)));
        // then
        response.andExpect(status().isBadRequest())
                .andDo(print());
    }

    // deleteById()
//    @Test
//    void givenDepartmentId_willDeleteDepartmentObject_True() throws Exception{
//        // given
//        Department department = Department.builder()
//                .id(1)
//                .name("HR")
//                .build();
//
//        departmentRepository.deleteById(1);
//
//        ResultActions response = mockMvc.perform(delete("/api/departments/{id}", department.getId()));
//
//        // then
//        response.andExpect(status().isOk())
//                .andDo(print());
//
//    }

    // deleteById()
    @Test
    void givenDepartmentId_willDeleteDepartmentObject_False() throws Exception{
        // given
        int id = 1;
        willThrow(DataNotFoundException.class).given(departmentRepository).deleteById(id);

        // when
        ResultActions response = mockMvc.perform(delete("/api/departments/{id}", id));

        // then
        response.andExpect(status().isBadRequest());
    }

}