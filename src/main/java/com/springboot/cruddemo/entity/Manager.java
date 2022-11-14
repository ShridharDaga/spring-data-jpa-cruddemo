package com.springboot.cruddemo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="manager")
@Data @Generated
@NoArgsConstructor
@Builder
@AllArgsConstructor
@ToString
public class Manager {
    @Id
    @Column(name="emp_id")
    private int empId;
    @Column(name="department_id", unique = true)
    private int deptId;

    @ToString.Exclude
    @JsonBackReference
    @OneToOne(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    @JoinColumn(name = "emp_id", referencedColumnName = "id")
    private Employee employee;

    @ToString.Exclude
    @JsonBackReference
    @OneToOne(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    @JoinColumn(name = "department_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Department department;

    public Manager(int empId, int deptId) {
        this.empId = empId;
        this.deptId = deptId;
    }
}
