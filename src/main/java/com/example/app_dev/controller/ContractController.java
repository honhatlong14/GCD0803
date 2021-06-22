package com.example.app_dev.controller;

import com.example.app_dev.model.Contract;
import com.example.app_dev.model.ContractDetail;
import com.example.app_dev.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@SessionAttributes("employeeSession")
@RequestMapping("/contract")
public class ContractController {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private ServiceService serviceService;
    @Autowired
    private ContractService contractService;
    @Autowired
    private AttachServiceService attachServiceService;
    @Autowired
    private ContractDetailService contractDetailService;

    @GetMapping("")
    public String getContractHome(Model model) {
        model.addAttribute("listContract", this.contractService.findAll());
        return "contract/contract_list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("listCustomer", this.customerService.findAllList());
        model.addAttribute("listEmployee", this.employeeService.findAllList());
        model.addAttribute("listService", this.serviceService.findAll());
        model.addAttribute("contract", new Contract());
        return "contract/create";
    }

    @PostMapping("/create")
    public String createContract(@Valid @ModelAttribute(name = "contract") Contract contract, BindingResult bindingResult,
                                 Model model, RedirectAttributes redirect) {
        new Contract().validate(contract, bindingResult);
        if (bindingResult.hasErrors()){
            model.addAttribute("listCustomer", this.customerService.findAllList());
            model.addAttribute("listEmployee", this.employeeService.findAllList());
            model.addAttribute("listService", this.serviceService.findAll());
            model.addAttribute("contract", contract);
            return "contract/create";
        } else {
            this.contractService.save(contract);
            redirect.addFlashAttribute("message", "Contract ID " + contract.getContractId() + " was added!");
            return "redirect:/contract/";
        }
    }

    @GetMapping("/createDetail")
    public String showContractDetailForm(@RequestParam(name = "contractId") Integer id, Model model) {
        model.addAttribute("contractDetail", new ContractDetail());
        model.addAttribute("contract", this.contractService.findById(id));
        model.addAttribute("listAttachService", this.attachServiceService.findAll());
        return "contract/create_contract_detail";
    }

    @PostMapping("/createDetail")
    public String createContractDetail(@ModelAttribute(name = "contractDetail") ContractDetail contractDetail,
                                       @RequestParam(name = "contractId") Integer id,
                                       RedirectAttributes redirect) {
        Contract contract = this.contractService.findById(id);
        contractDetail.setContract(contract);
        this.contractDetailService.save(contractDetail);  // save a new Contract Detail
        this.contractService.save(contract); // update contract : totalMoney
        redirect.addFlashAttribute("message",
                "Contract Detail of Contract " + contract.getContractId() + " was created!");
        return "redirect:/contract/";
    }

    @GetMapping("/viewDetail")
    public String viewDetailOfContractDetail(@RequestParam(name = "id") Integer id, Model model){
        Contract contract = this.contractService.findById(id);
        model.addAttribute("contract", contract);
        model.addAttribute("listContractDetail", this.contractDetailService.findAllByContract(contract));
        return "contract/detail_of_contract_detail";
    }
}
