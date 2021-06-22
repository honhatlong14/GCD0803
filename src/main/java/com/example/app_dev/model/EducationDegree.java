package com.example.app_dev.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "education_degree")
public class EducationDegree {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "education_degree_id")
    private Integer educationDegreeId;

    @Column(name = "education_degree_name", columnDefinition = "VARCHAR(45) NOT NULL")
    private String educationDegreeName;

    @OneToMany(mappedBy = "educationDegree", cascade = CascadeType.REMOVE)
    private Set<Employee> employeeSet;
}
