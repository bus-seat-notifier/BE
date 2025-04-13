package com.be.bus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/health")
public class HealthCheckController {
    @GetMapping
    public ResponseEntity<String> healthCheck(){
        return ResponseEntity.ok("bus 가보자고!!");
    }
}
