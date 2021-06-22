package com.example.app_dev.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Service {

    @Id
    @Column(name = "service_id", columnDefinition = "VARCHAR(45) NOT NULL")
    @NotNull
    @Pattern(regexp = "^DV-[\\d]{4}$", message = "Service ID must be in DV-XXXX format !\nExample : DV-0306")
    private String serviceId;

    @Column(name = "service_name", columnDefinition = "VARCHAR(45) NOT NULL")
    @NotNull(message = "Service Name can not be NULL")
    private String serviceName;

    @Column(name = "service_area", columnDefinition = "int NOT NULL", nullable = false)
    @Pattern(regexp = "^[\\d]+$", message = "Service Area must be a Integer number! \nExample : 60")
    private String serviceArea;

    @Column(name = "service_cost", columnDefinition = "DOUBLE NOT NULL" , nullable = false)
    @Pattern(regexp = "^[\\d]+(\\.[\\d]+)?$", message = "Service Cost must be a number! \nExample : 350 or 350.0")
    private String serviceCost;

    @Column(name = "service_max_people", columnDefinition = "int NOT NULL" , nullable = false)
    @Pattern(regexp = "^[\\d]+$", message = "Max People must be a Integer number! \nExample : 7")
    private String serviceMaxPeople;

    @Column(name = "standard_room", columnDefinition = "VARCHAR(45)")
    private String standardRoom;

    @Column(name = "description", columnDefinition = "VARCHAR(255)")
    private String description;

    @Column(name = "pool_area", columnDefinition = "DOUBLE NOT NULL")
    @Pattern(regexp = "^[\\d]+(\\.[\\d]+)?$", message = "Pool Area must be a number! \nExample : 75 or 75.0")
    private String poolArea;

    @Column(name = "number_of_floor", columnDefinition = "int NOT NULL")
    @Pattern(regexp = "^[\\d]+$", message = "Number of Floor must be a Integer number! \nExample : 8")
    private String numberOfFloor;

    @ManyToOne
    @JoinColumn(name = "rent_type_id", nullable = false)
    private RentType rentType;

    @ManyToOne
    @JoinColumn(name = "service_type_id", nullable = false)
    private ServiceType serviceType;

    @OneToMany(mappedBy = "service", cascade = CascadeType.ALL)
    private Set<Contract> contractSet;
}
