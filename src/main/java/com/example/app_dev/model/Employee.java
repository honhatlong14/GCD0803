package com.example.app_dev.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Employee implements Validator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private Integer employeeId;

    @Column(name = "employee_name", columnDefinition = "VARCHAR(45) NOT NULL")
    @NotNull(message = "Employee Name can not be NULL")
    private String employeeName;

    @Column(name = "employee_birthday", columnDefinition = "DATE")
    private String employeeBirthday;

    @Column(name = "employee_id_card", columnDefinition = "VARCHAR(45) UNIQUE NOT NULL")
    @NotNull
    @Pattern(regexp = "^[\\d]{9}|[\\d]{12}$",
            message = "Employee ID Card must contain 9 or 12 digits!\n Example : 205784694165")
    private String employeeIdCard;

    @Column(name = "employee_salary", columnDefinition = "DOUBLE")
    @Pattern(regexp = "^[\\d]+(\\.[\\d]+)?$", message = "Salary must be positive! Example : 750 or 750.0")
    private String employeeSalary;

    @Column(name = "employee_phone", columnDefinition = "VARCHAR(45)")
    @Pattern(regexp = "^(091|090|\\(84\\)\\+90|\\(84\\)\\+91)[\\d]{7}$",
            message = "Phone number must start with 090 or 091 or (84)+90 or (84)+91 \nExample : 0905456121 or (84)+912365478")
    private String employeePhone;

    @Column(name = "employee_email", columnDefinition = "VARCHAR(45)")
    @Pattern(regexp = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$",
            message = "Email must be in abc@abc.abc format!")
    private String employeeEmail;

    @Column(name = "employee_address", columnDefinition = "VARCHAR(255)")
    private String employeeAddress;

    @ManyToOne
    @JoinColumn(name = "position_id", nullable = false)
    private Position position;

    @ManyToOne
    @JoinColumn(name = "education_degree_id", nullable = false)
    private EducationDegree educationDegree;

    @ManyToOne
    @JoinColumn(name = "division_id", nullable = false)
    private Division division;

    @OneToOne
    @JoinColumn(name = "User_Name", nullable = false)
    private User user;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private Set<Contract> contractSet;

    @Override
    public boolean supports(Class<?> clazz) {
        return Employee.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object object, Errors errors) {
        Employee employee = (Employee) object;

        try {
            Date employeeBirthday = new SimpleDateFormat("yyyy-MM-dd").parse(employee.getEmployeeBirthday());
            Date currentDate = new Date();

            if (employeeBirthday.after(currentDate)){
                errors.rejectValue("employeeBirthday", "emp.birthday.afterCurrent");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
