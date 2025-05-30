package com.shirobokov.creditpipelinestaff.service;


import com.shirobokov.creditpipelinestaff.entity.Rule;
import com.shirobokov.creditpipelinestaff.repository.RuleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RuleService {
    private final RuleRepository ruleRepository;

    public RuleService(RuleRepository ruleRepository) {
        this.ruleRepository = ruleRepository;
    }

    public void save(Rule rule) {
        ruleRepository.save(rule);
    }

    public List<Rule> findAll() {
        return ruleRepository.findAll();
    }
    public void delete(Integer ruleId) {
        ruleRepository.deleteById(ruleId);
    }
}
