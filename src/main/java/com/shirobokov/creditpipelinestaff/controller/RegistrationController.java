package com.shirobokov.creditpipelinestaff.controller;

import com.shirobokov.creditpipelinestaff.entity.Employee;
import com.shirobokov.creditpipelinestaff.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {

    private EmployeeService employeeService;

    public RegistrationController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("employee", new Employee());

        return "registration";
    }

    @PostMapping("/registration")
    public String registrationEmployee(@ModelAttribute("employee") Employee employee, Model model) {
        System.out.println(employee.getUsername() + " " + employee.getPassword());
        //Проверка на корректность формата почты и длины пароля
        //Проверка содержание такого email в БД
        if (!employeeService.registrationEmployee(employee)) {
            model.addAttribute("alreadyRegistered", "Сотрудник уже зарегестрирован");
            return "registration";
        }
        model.addAttribute("success", "Сотрудник успешно зарегестрирован");
        return "registration";
    }
}
