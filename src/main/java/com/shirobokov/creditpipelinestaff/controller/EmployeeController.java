package com.shirobokov.creditpipelinestaff.controller;


import ch.qos.logback.core.model.Model;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EmployeeController {


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

}
