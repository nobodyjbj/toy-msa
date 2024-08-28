package org.toy.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.toy.userservice.service.UserService;
import org.toy.userservice.vo.Greeting;
import org.toy.userservice.vo.RequestUser;
import org.toy.userservice.vo.ResponseUser;

@RestController
@RequestMapping("/user-service/")
@RequiredArgsConstructor
public class UserController {
    private final Greeting greeting;
    private final Environment environment;
    private final UserService userService;

    @GetMapping("/health-check")
    public String healthCheck() {
        return String.format("It's working in UserService on PORT %s", environment.getProperty("local.server.port"));
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

    @GetMapping("/users")
    public ResponseEntity<Iterable<ResponseUser>> getUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findUsers());
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<ResponseUser> getUser(@PathVariable("userId") String userId) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserByUserId(userId));
    }
}
