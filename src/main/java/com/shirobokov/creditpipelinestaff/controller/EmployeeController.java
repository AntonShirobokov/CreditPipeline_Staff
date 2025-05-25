package com.shirobokov.creditpipelinestaff.controller;


import ch.qos.logback.core.model.Model;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EmployeeController {


    @GetMapping("/applications")
    public String applications(Model model) {
        return "applications";
    }

    @GetMapping("/test")
    public String test(Model model) {
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getAuthorities());
        return "applications";
    }

}
