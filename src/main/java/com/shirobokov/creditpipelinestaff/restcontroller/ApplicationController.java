package com.shirobokov.creditpipelinestaff.restcontroller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApplicationController {

    @PostMapping("/api/receivedApplication")
    @ResponseBody
    public ResponseEntity<?> receivedApplication(@RequestBody String receivedApplication) {
        return ResponseEntity.ok(receivedApplication);
    }

}
