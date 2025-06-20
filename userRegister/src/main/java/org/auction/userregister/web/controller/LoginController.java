package org.auction.userregister.web.controller;

import lombok.AllArgsConstructor;
import org.auction.userregister.service.AuthorizationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/path")
@AllArgsConstructor
public class LoginController {

    private final AuthorizationService authorizationService;

    @PostMapping
    public String login(@RequestParam String username, @RequestParam String password) {
        return authorizationService.login(username, password);
    }
}
