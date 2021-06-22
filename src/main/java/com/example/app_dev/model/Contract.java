package com.example.app_dev.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Contract implements Validator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contract_id")
    private Integer contractId;

    @Column(name = "contract_start_date", columnDefinition = "DATE NOT NULL")
    private String contractStartDate;

    @Column(name = "contract_end_date", columnDefinition = "DATE")
    private String contractEndDate;

    @Column(name = "contract_deposit", columnDefinition = "DOUBLE NOT NULL")
    @Pattern(regexp = "^[\\d]+(\\.[\\d]+)?$", message = "Contract Deposit must be a number! \nExample : 1000 or 1000.0")
    private String contractDeposit;

    @Column(name = "contract_total_money")
    private double contractTotalMoney;

    @ManyToOne
    @JoinColumn(name = "service_id", nullable = false)
    private Service service;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @OneToMany(mappedBy = "contract", cascade = CascadeType.ALL)
    private Set<ContractDetail> contractDetailSet;

    @Override
    public boolean supports(Class<?> clazz) {
        return Contract.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object object, Errors errors) {
        Contract contract = (Contract) object;
        try {
            Date start = new SimpleDateFormat("yyyy-MM-dd").parse(contract.getContractStartDate());
            Date end = new SimpleDateFormat("yyyy-MM-dd").parse(contract.getContractEndDate());

            Date currentDate = new Date();

            if (start.after(end)) {
                errors.rejectValue("contractStartDate", "con.start.afterEnd");
            }
            if (end.before(start)) {
                errors.rejectValue("contractEndDate", "con.end.beforeStart");
            }
            if (end.before(currentDate)){
                errors.rejectValue("contractEndDate", "con.end.beforeCurrent");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
