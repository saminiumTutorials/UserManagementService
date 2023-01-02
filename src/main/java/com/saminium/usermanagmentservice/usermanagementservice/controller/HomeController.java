package com.saminium.usermanagmentservice.usermanagementservice.controller;

import com.saminium.usermanagmentservice.usermanagementservice.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/api")
public class HomeController {

    User user1 = User.builder().userId(1L).userName("user1").lastName("lastName1").email("email1@mail.com").build();
    User user2 = User.builder().userId(2L).userName("user2").lastName("lastName2").email("email2@mail.com").build();
    final List<User> users  = List.of(user1, user2);

    @GetMapping("/")
    public ResponseEntity<?> swagger(){
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create("http://localhost:8080/swagger-ui.html")).build();
    }

}
