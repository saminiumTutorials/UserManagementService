package com.saminium.usermanagmentservice.usermanagementservice.controller;

import com.saminium.usermanagmentservice.usermanagementservice.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    User user1 = User.builder().userId(1L).userName("user1").lastName("lastName1").email("email1@mail.com").build();
    User user2 = User.builder().userId(2L).userName("user2").lastName("lastName2").email("email2@mail.com").build();
    final List<User> users  = List.of(user1, user2);

    @PostMapping("/v1/user")
    public ResponseEntity<?> addUser(@RequestBody User user){
        users.add(user);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(users);
    }

    @DeleteMapping("/v1/user/{userId}")
    public ResponseEntity<?> addUser(@PathVariable long userId){
        User userToDelete = users.stream().filter(user -> user.getUserId().longValue() == userId).findFirst().get();
        users.remove(userToDelete);

        return ResponseEntity.status(HttpStatus.OK)
                .body(users);
    }

    @GetMapping("/v1/user")
    public ResponseEntity<List<User>> users(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(users);
    }


    @PutMapping("/v1/user")
    public ResponseEntity<User> updateUser(@RequestBody User user){
        final User existingUser = users.stream().filter(u -> u.getUserId() == user.getUserId()).findFirst().get();
        //let's just update last name
        existingUser.setLastName(user.getLastName());
        return ResponseEntity.status(HttpStatus.OK)
                .body(existingUser);
    }
}
