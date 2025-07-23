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
/*
    @GetMapping("/{username}")
    public ResponseEntity<String> getUserByUsername(@PathVariable String username) {
        return userService.getUserByUsername(username);
    }*/

    /*@PutMapping("/update/{username}")
    public ResponseEntity<String> updateUser(@PathVariable String username, @RequestBody UserDTO updatedUser) {
        return userService.updateUser(username, updatedUser);
    }
*/
   /* @DeleteMapping("/delete/{username}")
    public ResponseEntity<String> deleteUser(@PathVariable String username) {
        return userService.deleteUser(username);
    }
*/
   /* @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
      return   userService.authenticate(loginRequest);

    }*/

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody LoginRequest request) {
        return userService.authenticate(request);
    }


}
