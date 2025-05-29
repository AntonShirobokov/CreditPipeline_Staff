package com.shirobokov.creditpipelinestaff.controller;


import ch.qos.logback.core.model.Model;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EmployeeController {

    @Value("${spring.mail.username}")
    public String usernameEmail;

    @Value("${spring.mail.password}")
    public String passwordEmail;

    @GetMapping("/evaluate")
    public String evaluate(Model model) {
        return "evaluate";
    }

    @GetMapping("/rules")
    public String rules(Model model) {
        return "rules";
    }

    @GetMapping("/history")
    public String history(Model model) {
        return "history";
    }

    @GetMapping("/test")
    public String test() {

        System.out.println("Логин от почты: " + usernameEmail);
        System.out.println("Пароль от почты: " + passwordEmail);

        return "evaluate";
    }

}
