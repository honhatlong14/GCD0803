package com.example.app_dev.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table( name = "Role",
        uniqueConstraints = {
        @UniqueConstraint(name = "ROLE_UK", columnNames = "Role_Name")
        })
public class Role {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "Role_Id")
        private Integer id;

        @Column(name = "Role_Name", columnDefinition = "VARCHAR(45) NOT NULL")
        private String roleName;

}
