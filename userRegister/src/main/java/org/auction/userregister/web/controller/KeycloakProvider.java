package org.auction.userregister.web.controller;

import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class KeycloakProvider {
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password) {
        System.out.println(username);
        String keycloakUrl = "http://localhost:8010/realms/emiter/protocol/openid-connect/token";

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("client_id", "legal");
        form.add("client_secret", "a866CGcL4IwFIDemNWD5teNDpxTFtLGl");
        form.add("grant_type", "password");
        form.add("username", username);
        form.add("password", password);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(form, headers);
        ResponseEntity<String> response = restTemplate.exchange(keycloakUrl, HttpMethod.POST, request, String.class);

        return response;
    }


    @PostMapping("/loginWith")
    public String loginWith(@RequestParam String username, @RequestParam String password) {
        RestTemplate restTemplate = new RestTemplate();


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        System.out.println(username);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("client_id", "legal");
        map.add("client_secret", "a866CGcL4IwFIDemNWD5teNDpxTFtLGl");
        map.add("grant_type", "password");
        map.add("username", username);
        map.add("password", password);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);

        String url = "http://localhost:8010/realms/emiter/protocol/openid-connect/token";

        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
        return response.getBody();
    }
}
