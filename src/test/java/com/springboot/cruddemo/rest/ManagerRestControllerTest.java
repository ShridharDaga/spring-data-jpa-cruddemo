package com.springboot.cruddemo.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.cruddemo.dto.ManagerDTO;
import com.springboot.cruddemo.entity.Manager;
import com.springboot.cruddemo.service.ManagerService;
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
class ManagerRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ManagerService managerService;

    @Autowired
    private ObjectMapper objectMapper;

    // findAll()
    @Test
    void givenListOfManagers_willReturnManagersList() throws Exception {
        // given (setup)
        List<Manager> managers = new ArrayList<>();

        managers.add(Manager.builder().empId(1).deptId(2).build());
        managers.add(Manager.builder().empId(2).deptId(3).build());

        given(managerService.findAll()).willReturn(managers);

        // when
        ResultActions response = mockMvc.perform(get("/api/managers"));

        // then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()", is(managers.size())));
    }

    // findById()
    @Test
    void givenManagerId_willReturnManagerObject_True() throws Exception{
        // given
        int id = 1;
        Manager manager = Manager.builder()
                .empId(1)
                .deptId(2)
                .build();

        given(managerService.findById(id)).willReturn(manager);

        // when
        ResultActions response = mockMvc.perform(get("/api/managers/{id}", id));

        // then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.empId", is(manager.getEmpId())))
                .andExpect(jsonPath("$.deptId", is(manager.getDeptId())));
    }

    @Test
    void givenManagerId_willReturnManagerObject_False() throws Exception{

        // given
        given(managerService.findById(1)).willReturn(null).willThrow(DataNotFoundException.class);

        mockMvc.perform(get("/api/managers/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }

    // save()
    @Test
    void givenManagerObject_writeManagerObject() throws Exception{
        // given
        ManagerDTO manager = ManagerDTO.builder()
                .empId(1)
                .deptId(2)
                .build();

        when(managerService.save(manager)).thenReturn(manager);

        // when
        ResultActions response = mockMvc.perform(post("/api/managers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(manager)));
        // then
        response.andExpect(status().isOk())
                .andDo(print()).andExpect(jsonPath("$.empId", is(manager.getEmpId())))
                .andExpect(jsonPath("$.deptId", is(manager.getDeptId())));
    }

    @Test
    void saveManagerTest() {
        ManagerDTO manager = ManagerDTO.builder()
                .empId(1)
                .deptId(2)
                .build();

        when(managerService.save(manager)).thenReturn(manager);
        Assertions.assertEquals(manager, managerService.save(manager));
    }

    // update()
    @Test
    void givenManagerObject_writeUpdatedManagerObject_True() throws Exception{
        // given
        ManagerDTO manager = ManagerDTO.builder()
                .empId(1)
                .deptId(2)
                .build();

        when(managerService.save(manager)).thenReturn(manager);

        // when
        ResultActions response = mockMvc.perform(put("/api/managers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(manager)));
        // then
        response.andExpect(status().isOk())
                .andDo(print()).andDo(print()).andExpect(jsonPath("$.empId", is(manager.getEmpId())))
                .andExpect(jsonPath("$.deptId", is(manager.getDeptId())));
    }

    @Test
    void givenManagerObject_writeUpdatedManagerObject_False() throws Exception{
        // given
        ManagerDTO manager = ManagerDTO.builder()
                .empId(1)
                .deptId(2)
                .build();

        when(managerService.save(manager)).thenThrow(IllegalArgumentException.class);

        // when
        ResultActions response = mockMvc.perform(put("/api/managers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(manager)));
        // then
        response.andExpect(status().isBadRequest())
                .andDo(print());
    }

    // deleteById()
    @Test
    void givenManagerId_willDeleteManagerObject_True() throws Exception{
        // given
        Manager manager = Manager.builder()
                .empId(1)
                .deptId(2)
                .build();

        managerService.deleteById(manager.getEmpId());

        ResultActions response = mockMvc.perform(delete("/api/managers/{id}", manager.getEmpId()));

        // then
        response.andExpect(status().isNotFound())
                .andDo(print());

    }

    // deleteById()
    @Test
    void givenManagerId_willDeleteManagerObject_False() throws Exception{
        // given
        int id = 1;
        willThrow(DataNotFoundException.class).given(managerService).deleteById(id);

        // when
        ResultActions response = mockMvc.perform(delete("/api/managers/{id}", id));

        // then
        response.andExpect(status().isNotFound());
    }
}