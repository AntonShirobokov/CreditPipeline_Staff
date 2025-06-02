package com.shirobokov.creditpipelinestaff.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @GetMapping("/statistics")
    public String statistics() {
        return "statistics";
    }

    @GetMapping("/search")
    public String search() {
        return "search";
    }
}
