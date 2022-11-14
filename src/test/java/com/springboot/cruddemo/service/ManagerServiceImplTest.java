package com.springboot.cruddemo.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.cruddemo.dao.ManagerRepository;
import com.springboot.cruddemo.entity.Manager;
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
class ManagerServiceImplTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ManagerRepository managerRepository;


    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void deleteMethodShouldOnce(){
        Manager manager = Manager.builder()
                .empId(1)
                .deptId(2)
                .build();
        managerRepository.deleteById(manager.getEmpId());
        then(managerRepository).should(times(1)).deleteById(manager.getEmpId());
    }

    // findAll()
    @Test
    void givenListOfManagers_willReturnManagersList() throws Exception {
        // given (setup)
        List<Manager> managers = new ArrayList<>();

        managers.add(Manager.builder().empId(1).deptId(2).build());
        managers.add(Manager.builder().empId(2).deptId(3).build());

        given(managerRepository.findAll()).willReturn(managers);

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

        given(managerRepository.findById(id)).willReturn(Optional.ofNullable(manager));

        // when
        ResultActions response = mockMvc.perform(get("/api/managers/{id}", id));

        // then
        assert manager != null;
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.empId", is(manager.getEmpId())))
                .andExpect(jsonPath("$.deptId", is(manager.getDeptId())));
    }

    @Test
    void givenManagerId_willReturnManagerObject_False() throws Exception{

        // given
        given(managerRepository.findById(1)).willThrow(IllegalArgumentException.class);

        mockMvc.perform(get("/api/managers/{id}", "null")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

    // save()
    @Test
    void givenManagerObject_writeManagerObject() throws Exception{
        // given
        Manager manager = Manager.builder()
                .empId(1)
                .deptId(2)
                .build();

        when(managerRepository.save(manager)).thenReturn(manager);

        // when
        ResultActions response = mockMvc.perform(post("/api/managers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(manager)));
        // then
        response.andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    void saveManagerTest() {
        Manager manager = Manager.builder()
                .empId(1)
                .deptId(2)
                .build();

        when(managerRepository.save(manager)).thenReturn(manager);
        Assertions.assertEquals(manager, managerRepository.save(manager));
    }

    // update()
    @Test
    void givenManagerObject_writeUpdatedManagerObject() throws Exception{
        // given
        Manager manager = Manager.builder()
                .empId(1)
                .deptId(2)
                .build();

        when(managerRepository.save(manager)).thenReturn(manager);

        // when
        ResultActions response = mockMvc.perform(put("/api/managers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(manager)));
        // then
        response.andExpect(status().isBadRequest())
                .andDo(print());
    }

    // deleteById()
//    @Test
//    void givenManagerId_willDeleteManagerObject_True() throws Exception{
//        // given
//        Manager manager = Manager.builder()
//                .empId(1)
//                .deptId(2)
//                .build();
//
//        managerRepository.deleteById(manager.getId());
//
//        ResultActions response = mockMvc.perform(delete("/api/managers/{id}", manager.getId()));
//
//        // then
//        response.andExpect(status().isBadRequest())
//                .andDo(print());
//
//    }

    // deleteById()
    @Test
    void givenManagerId_willDeleteManagerObject_False() throws Exception{
        // given
        int id = 1;
        willDoNothing().given(managerRepository).deleteById(id);

        // when
        ResultActions response = mockMvc.perform(delete("/api/managers/{id}", id));

        // then
        response.andExpect(status().isBadRequest());
    }
}