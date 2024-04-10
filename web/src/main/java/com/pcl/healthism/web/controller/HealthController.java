package com.pcl.healthism.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
public class HealthController {

    @GetMapping("/isAlive")
    public String testHeartBeat() {
        return "ok";
    }

    @GetMapping("/isAlive/ok")
    public String testHeartBeatabc() {
        return "ok123";
    }
}
