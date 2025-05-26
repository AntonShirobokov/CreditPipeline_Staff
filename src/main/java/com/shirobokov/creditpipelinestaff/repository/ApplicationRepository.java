package com.shirobokov.creditpipelinestaff.repository;


import com.shirobokov.creditpipelinestaff.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer> {

    List<Application> findByStatus(String status);

}
