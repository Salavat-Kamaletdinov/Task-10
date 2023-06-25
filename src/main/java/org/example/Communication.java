package org.example;

import org.example.entity.User;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class Communication {
    private final RestTemplate restTemplate;
    private final HttpHeaders headers;
    private String url = "http://94.198.50.185:7081/api/users";

    public Communication(RestTemplate restTemplate, HttpHeaders headers) {
        this.restTemplate = restTemplate;
        this.headers = headers;
        this.headers.set("Cookie", String.join(";", restTemplate.headForHeaders(url).get("Set-Cookie")));
    }

    private List<User> getAllUsers() {
        ResponseEntity<List<User>> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<User>>() {
        });
        System.out.println(responseEntity.getHeaders());
        return responseEntity.getBody();
    }

    private ResponseEntity<String> addUser() {
        User user = new User(3L, "James", "Brown", (byte) 20);
        HttpEntity<User> entity = new HttpEntity<>(user, headers);
        return restTemplate.postForEntity(url, entity, String.class);
    }

    private ResponseEntity<String> updateUser() {
        User user = new User(3L, "Thomas", "Shelby", (byte) 20);
        HttpEntity<User> entity = new HttpEntity<>(user, headers);
        return restTemplate.exchange(url, HttpMethod.PUT, entity, String.class, 3);
    }

    private ResponseEntity<String> deleteUser() {
        Map<String, Long> variable = new HashMap<>() {{
            put("id", 3L);
        }};
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        return restTemplate.exchange(url + "/{id}", HttpMethod.DELETE, entity, String.class, variable);
    }

    public String allMethods(){
        return addUser().getBody() + updateUser().getBody() + deleteUser().getBody();
    }
}
