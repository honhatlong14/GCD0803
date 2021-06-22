package com.example.app_dev.controller;

import com.example.app_dev.model.Customer;
import com.example.app_dev.service.ContractService;
import com.example.app_dev.service.CustomerService;
import com.example.app_dev.service.CustomerTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@SessionAttributes("employeeSession")
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private CustomerTypeService customerTypeService;
    @Autowired
    private ContractService contractService;

    @GetMapping("")
    public String getCustomerHome(Model model, @PageableDefault(value = 5) Pageable pageable){
        model.addAttribute("listCustomer", this.customerService.findAll(pageable));
        return "customer/customer_list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model){
        model.addAttribute("customer", new Customer());
        model.addAttribute("listCustomerType", this.customerTypeService.findAll());
        return "customer/create";
    }

    @PostMapping("/create")
    public String createCustomer(@Valid @ModelAttribute(name = "customer") Customer customer, BindingResult bindingResult,
                                 Model model, RedirectAttributes redirect){
        new Customer().validate(customer,bindingResult);
        this.customerService.validateCustomerIdExist(customer, bindingResult);

        if (bindingResult.hasErrors()){
            model.addAttribute("customer", customer);
            model.addAttribute("listCustomerType", this.customerTypeService.findAll());
            return "customer/create";
        } else {
            this.customerService.save(customer);
            redirect.addFlashAttribute( "message",
                                        "Customer "+customer.getCustomerName()+" was added!");
            return "redirect:/customer/";
        }
    }

    @GetMapping("/view")
    public String viewCustomer(@RequestParam(name = "id") String id, Model model){
        Customer customer = this.customerService.findById(id);
        model.addAttribute("customer", customer);
        return "customer/view";
    }

    @GetMapping("/edit")
    public String showEditForm(@RequestParam(name = "id") String id, Model model){
        Customer customer = this.customerService.findById(id);
        if (customer == null) {
            return "404";
        }
        model.addAttribute("listCustomerType", this.customerTypeService.findAll());
        model.addAttribute("customer", customer);
        return "customer/edit";
    }

    @PostMapping("/edit")
    public String editCustomer(@Valid Customer customer, BindingResult bindingResult,
                               Model model, RedirectAttributes redirect){

        new Customer().validate(customer,bindingResult);

        if (bindingResult.hasErrors()){
            model.addAttribute("listCustomerType", this.customerTypeService.findAll());
            model.addAttribute("customer", customer);
            return "customer/edit";
        } else {
            this.customerService.save(customer);
            redirect.addFlashAttribute("message","Information of Customer "+customer.getCustomerName()+" was updated!");
            return "redirect:/customer/";
        }
    }

    @GetMapping("/delete")
    public String showDeleteForm(@RequestParam(name = "id") String id, Model model){
        Customer customer = this.customerService.findById(id);
        model.addAttribute("customer", customer);
        return "customer/delete";
    }

    @PostMapping("/delete")
    public String deleteCustomer(@RequestParam(name = "customerId") String id, RedirectAttributes redirect){
        Customer customer = this.customerService.findById(id);
        this.customerService.deleteById(id);
        redirect.addFlashAttribute("message", "Customer "+customer.getCustomerName()+" was deleted!");
        return "redirect:/customer/";
    }

    @GetMapping("/search")
    public String searchCustomerByName(@RequestParam("search") Optional<String> search,
                                       @PageableDefault(value = 5) Pageable pageable,
                                       Model model){
        Page<Customer> listCustomer;
        if (search.isPresent()){
            model.addAttribute("search", search.get());
            listCustomer = this.customerService.searchCustomer(search.get(), pageable);
        } else {
            listCustomer = this.customerService.findAll(pageable);
        }
        model.addAttribute("listCustomer", listCustomer);
        return "customer/customer_list";
    }

    @GetMapping("/using")
    public String showUsingCustomer(Model model, @PageableDefault(value = 5) Pageable pageable){
        String date = this.contractService.getCurrentDate();
        model.addAttribute("listUsingCustomer", this.contractService.getListUsingCustomer(date, pageable));
        return "customer/using";
    }
}