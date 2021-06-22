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
public class Division {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "division_id")
    private Integer divisionId;

    @Column(name = "division_name", columnDefinition = "VARCHAR(45) NOT NULL")
    private String divisionName;

    @OneToMany(mappedBy = "division", cascade = CascadeType.REMOVE)
    private Set<Employee> employeeSet;

}
