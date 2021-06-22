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
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Customer implements Validator {

    @Id
    @Column(name = "customer_id", columnDefinition = "VARCHAR(45) NOT NULL")
    @NotNull
    @Pattern(regexp = "^KH-[\\d]{4}$",
            message = "Customer ID must be in KH-XXXX format !\n Example : KH-4569")
    private String customerId;

    @Column(name = "customer_name", columnDefinition = "VARCHAR(45) NOT NULL")
    @NotNull(message = "Customer Name can not be NULL")
    private String customerName;

    @Column(name = "customer_birthday", columnDefinition = "DATE")
    private String customerBirthday;

    @Column(name = "customer_gender")
    private boolean customerGender;

    @Column(name = "customer_id_card", columnDefinition = "VARCHAR(45) NOT NULL UNIQUE")
    @NotNull
    @Pattern(regexp = "^[\\d]{9}|[\\d]{12}$",
            message = "Customer ID Card must contain 9 or 12 digits!\n Example : 205145365")
    private String customerIdCard;

    @Column(name = "customer_phone", columnDefinition = "VARCHAR(45)")
    @Pattern(regexp = "^(091|090|\\(84\\)\\+90|\\(84\\)\\+91)[\\d]{7}$",
            message = "Phone start with 090 or 091 or (84)+90 or (84)+91 \nExample : 0905456121 or (84)+912365478")
    private String customerPhone;

    @Column(name = "customer_email", columnDefinition = "VARCHAR(45)")
    @Pattern(regexp = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$",
            message = "Email must be in abc@abc.abc format!")
    private String customerEmail;

    @Column(name = "customer_address", columnDefinition = "VARCHAR(255)")
    private String customerAddress;

    @ManyToOne
    @JoinColumn(name = "customer_type_id", nullable = false)
    private CustomerType customerType;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private Set<Contract> contractSet;

    @Override
    public boolean supports(Class<?> clazz) {
        return Customer.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object object, Errors errors) {
        Customer customer = (Customer) object;

        try {
            Date birthday = new SimpleDateFormat("yyyy-MM-dd").parse(customer.getCustomerBirthday());
            Date currentDate = new Date();

            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.YEAR, -18);
            Date dateOf18YearsAgo = calendar.getTime();

            if (birthday.after(currentDate)){
                errors.rejectValue("customerBirthday", "cus.birthday.afterCurrent");
            }
            if (birthday.after(dateOf18YearsAgo)) {
                errors.rejectValue("customerBirthday", "cus.birthday.notEnough18");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
