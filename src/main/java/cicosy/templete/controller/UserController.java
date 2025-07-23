package cicosy.templete.controller;


import cicosy.templete.domain.User;

import cicosy.templete.dto.LoginRequest;
import cicosy.templete.dto.UserDTO;
import cicosy.templete.dto.UserRegistrationRequest;
import cicosy.templete.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/hello-world")
    public String helloWorld() {
        return "hello world";
    }

    @PostMapping("/create-user")
    public User createUser(@RequestBody UserDTO user) {
        User newUser = new User();
        return userService.registerUser(user.getUsername(),user.getEmail(), user.getPassword());
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody LoginRequest request) {
        return userService.authenticate(request);
    }


}
