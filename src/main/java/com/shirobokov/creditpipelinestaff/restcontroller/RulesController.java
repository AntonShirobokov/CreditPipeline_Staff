package com.shirobokov.creditpipelinestaff.restcontroller;


import com.shirobokov.creditpipelinestaff.entity.Rule;
import com.shirobokov.creditpipelinestaff.service.RuleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class RulesController {

    private final RuleService ruleService;

    public RulesController(RuleService ruleService) {
        this.ruleService = ruleService;
    }


    @PostMapping("/api/rules/addRule")
    public ResponseEntity<?> addRule(@RequestBody Rule rule) {
        System.out.println("добавление правила");
        ruleService.save(rule);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/api/rules/getAllRules")
    public ResponseEntity<?> getAllRules() {
        return ResponseEntity.ok(ruleService.findAll());
    }

    @DeleteMapping("/api/rules/deleteRule")
    public ResponseEntity<?> deleteRule(@RequestBody Integer ruleId) {
        ruleService.delete(ruleId);
        return ResponseEntity.ok().build();
    }
}
