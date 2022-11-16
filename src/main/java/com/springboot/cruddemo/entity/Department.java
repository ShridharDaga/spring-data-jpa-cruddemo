package com.springboot.cruddemo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="department")
@Data @Generated
@NoArgsConstructor
@Builder
@AllArgsConstructor
@ToString
public class Department {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="name")
    private String name;

    @ToString.Exclude
    @JsonManagedReference
    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY,
            mappedBy = "department",
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
            orphanRemoval = true)
    private Manager manager;

    @ToString.Exclude
    @JsonManagedReference
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "department",
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private List<Employee> employees;


}

