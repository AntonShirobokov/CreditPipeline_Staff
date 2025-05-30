package com.shirobokov.creditpipelinestaff.repository;


import com.shirobokov.creditpipelinestaff.entity.Rule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RuleRepository extends JpaRepository<Rule, Integer> {


}
