package com.springboot.cruddemo.dto;

import lombok.*;

@Data
@Generated
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeDTO {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String deptId;
}
