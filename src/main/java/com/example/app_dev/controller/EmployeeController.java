package com.example.app_dev.controller;

import com.example.app_dev.model.Employee;
import com.example.app_dev.model.User;
import com.example.app_dev.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@SessionAttributes("employeeSession")
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private PositionService positionService;
    @Autowired
    private DivisionService divisionService;
    @Autowired
    private EducationDegreeService educationService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRoleService userRoleService;

    @GetMapping("")
    public String getEmployeeHome(@PageableDefault(value = 5) Pageable pageable,
                                  Model model){
        model.addAttribute("listEmployee", this.employeeService.findAll(pageable));
        return "employee/employee_list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model){
        model.addAttribute("listEducation", this.educationService.findAll());
        model.addAttribute("listDivision", this.divisionService.findAll());
        model.addAttribute("listPosition", this.positionService.findAll());
        model.addAttribute("employee", new Employee());
        return "employee/create";
    }

    @PostMapping("/create")
    public String createEmployee(@Valid @ModelAttribute(name = "employee") Employee employee, BindingResult bindingResult,
                                 Model model,
                                 @RequestParam(name = "inputUsername") String inputUsername,
                                 RedirectAttributes redirect){
        new Employee().validate(employee, bindingResult);
        this.userService.validateUsernameExist(inputUsername, bindingResult);

        if (bindingResult.hasErrors()){
            if (bindingResult.hasFieldErrors("user")) {
                model.addAttribute("usernameMsg", "Username was existed!");
            }
            model.addAttribute("listEducation", this.educationService.findAll());
            model.addAttribute("listDivision", this.divisionService.findAll());
            model.addAttribute("listPosition", this.positionService.findAll());
            model.addAttribute("inputUsername", inputUsername);
            model.addAttribute("employee", employee);
            return "employee/create";
        } else {
            //tạo user sau khi đã check trùng username ở trên
            com.example.app_dev.model.User user = this.userService.createUserByUsername(inputUsername);
            employee.setUser(user);

            this.employeeService.saveCrypt(employee); // add a whole new Employee : hash password into a bCrypt pw
            this.userRoleService.createUserRole(user, employee); // tạo role cho Employee dựa trên Position của Employee

            redirect.addFlashAttribute("message", "Employee "+employee.getEmployeeName()+" was added!");
            return "redirect:/employee/";
        }
    }

    @GetMapping("/view")
    public String viewEmployee(@RequestParam(name = "id") Integer id, Model model){
        model.addAttribute("employee", this.employeeService.findById(id));
        return "employee/view";
    }

    @GetMapping("/edit")
    public String showEditForm(@RequestParam(name = "id") Integer id, Model model){
        Employee employee = this.employeeService.findById(id);
        if (employee == null) {
            return "404";
        }
        model.addAttribute("listEducation", this.educationService.findAll());
        model.addAttribute("listDivision", this.divisionService.findAll());
        model.addAttribute("listPosition", this.positionService.findAll());
        model.addAttribute("employee", employee);
        return "employee/edit";
    }

    @PostMapping("/edit")
    public String editEmployee(@Valid @ModelAttribute("employee") Employee employee, BindingResult bindingResult,
                               Model model,
                               RedirectAttributes redirect){
        new Employee().validate(employee, bindingResult);
        if (bindingResult.hasErrors()){
            model.addAttribute("listEducation", this.educationService.findAll());
            model.addAttribute("listDivision", this.divisionService.findAll());
            model.addAttribute("listPosition", this.positionService.findAll());
            model.addAttribute("employee", employee);
            return "employee/edit";
        } else {
            this.employeeService.save(employee); // update employee information
            this.userRoleService.updateUserRole(employee); // update role of employee in user_role table
            redirect.addFlashAttribute( "message",
                    "Information of Employee "+employee.getEmployeeName()+" was updated!");
            return "redirect:/employee/";
        }
    }

    @GetMapping("/delete")
    public String showDeleteForm(@RequestParam(name = "id") Integer id, Model model){
        model.addAttribute("employee", this.employeeService.findById(id));
        return "employee/delete";
    }

    @PostMapping("/delete")
    public String deleteEmployee(@RequestParam(name = "employeeId") Integer id, RedirectAttributes redirect){
        Employee employee = this.employeeService.findById(id);
        employee.getUser().setEnabled(false); // set Enabled của account = 0
        this.employeeService.deleteById(id); // xóa Employee sẽ không xóa account của employee đó ,mà sẽ set Enabled = 0;
        redirect.addFlashAttribute( "message",
                                    "Employee "+employee.getEmployeeName()+" was deleted!");
        return "redirect:/employee/";
    }

    @GetMapping("/changePw")
    public String showChangePasswordForm(@RequestParam(name = "id") Integer id,
                                         Principal principal, Model model){
        String username = principal.getName();
        User user = this.userService.findByUsername(username);
        Employee employeeSession = this.employeeService.findByUser(user);
        Employee employee = this.employeeService.findById(id);
        if (employee == null) {
            return "404";
        }
        if (this.userRoleService.isDirectorRole(user)){
            model.addAttribute("id", id);
            model.addAttribute("employee", employee);
            return "employee/changePw";
        }
        if (employeeSession.getEmployeeId().equals(id)){
            model.addAttribute("id", id);
            model.addAttribute("employee", employee);
            return "employee/changePw";
        } else {
            return "403";
        }
    }

    @PostMapping("/changePw")
    public String changePassword(@RequestParam(name = "id") Integer id,
                                 @RequestParam(name = "oldPw") String oldPw,
                                 @RequestParam(name = "newPw") String newPw,
                                 @RequestParam(name = "confirmPw") String confirmPw,
                                 Model model,
                                 RedirectAttributes redirect){
        Employee employee = this.employeeService.findById(id);
        if (this.employeeService.checkPassword(oldPw, employee)) {
            if (newPw.equals(confirmPw)){
                employee.getUser().setPassword(newPw);
                this.employeeService.saveCrypt(employee); // update lại employee : user : password
                redirect.addFlashAttribute("message",
                        "Update password for Employee "+ employee.getEmployeeName() +" successfully!");
                return "redirect:/employee/";
            } else {
                model.addAttribute("oldPw", oldPw);
                model.addAttribute("id", id);
                model.addAttribute("employee", this.employeeService.findById(id));
                model.addAttribute("messageConfirm","The Confirm Password confirmation does not match.");
                return "employee/changePw";
            }
        } else {
            model.addAttribute("oldPw",oldPw);
            model.addAttribute("id", id);
            model.addAttribute("employee", this.employeeService.findById(id));
            model.addAttribute("messageOldPw","Oops, That's wasn't the right password. Try again");
            return "employee/changePw";
        }
    }
}
