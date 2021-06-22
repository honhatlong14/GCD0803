package com.example.app_dev.controller;

import com.example.app_dev.model.Service;
import com.example.app_dev.service.RentTypeService;
import com.example.app_dev.service.ServiceService;
import com.example.app_dev.service.ServiceTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@SessionAttributes("employeeSession")
@RequestMapping("/service")
public class ServiceController {

    @Autowired
    ServiceService serviceService;

    @Autowired
    ServiceTypeService serviceTypeService;

    @Autowired
    RentTypeService rentTypeService;

    @GetMapping("")
    public String getServiceHome(Model model){
        model.addAttribute("listService", this.serviceService.findAll());
        return "service/service_list";
    }

    @GetMapping("/create")
    public String showCreateForm(@RequestParam("type") Integer type, Model model){
        if ((type != 1 &&  type != 2 && type != 3)) {
            return "404";
        }
        model.addAttribute("type",type);
        model.addAttribute("listServiceType", this.serviceTypeService.findAll());
        model.addAttribute("listRentType", this.rentTypeService.findAll());
        model.addAttribute("service", new Service());
        return "service/create";
    }

    @PostMapping("/create")
    public String createService(@Valid @ModelAttribute(name = "service") Service service, BindingResult bindingResult,
                                Model model,
                                @RequestParam(name = "type") Integer type,
                                RedirectAttributes redirect){

        this.serviceService.validateServiceIdExist(service, bindingResult);

        if (bindingResult.hasErrors()){
            model.addAttribute("type",type);
            model.addAttribute("listServiceType", this.serviceTypeService.findAll());
            model.addAttribute("listRentType", this.rentTypeService.findAll());
            model.addAttribute("service", service);
            return "service/create";
        } else {
            service.setServiceName(this.serviceService.createServiceName(service));
            this.serviceService.save(service);
            redirect.addFlashAttribute("message", "Service "+service.getServiceName()+" was added!");
            return "redirect:/service/";
        }
    }

    @GetMapping("/view")
    public String viewService(@RequestParam(name = "id") String id, Model model){
        Service service = this.serviceService.findById(id);
        model.addAttribute("service", service);
        return "service/view";
    }
}
