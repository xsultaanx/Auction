package com.auction.core.web.contoller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class TestController {

    @GetMapping("/api")
    public ResponseEntity<String> api(Principal principal) {
        System.out.println(principal.getName());
        return ResponseEntity.ok("Hello, secured world!"+principal.toString());
    }
    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Hello, secured world!");
    }
}