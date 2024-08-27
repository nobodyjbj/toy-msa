package org.toy.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.toy.userservice.service.UserService;
import org.toy.userservice.vo.Greeting;
import org.toy.userservice.vo.RequestUser;
import org.toy.userservice.vo.ResponseUser;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class UserController {
    private final Greeting greeting;
    private final UserService userService;

    @GetMapping("/health_check")
    public String healthCheck() {
        return "It's working in UserService.";
    }

    @GetMapping("/welcome")
    public String welcome() {
        return greeting.getMessage();
    }

    @PostMapping("/users")
    public ResponseEntity<ResponseUser> createUser(@RequestBody RequestUser requestUser){
        ResponseUser user = userService.createUser(requestUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }
}
