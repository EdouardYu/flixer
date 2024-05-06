package web.technologies.flixer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import web.technologies.flixer.dto.AuthenticationDTO;
import web.technologies.flixer.entity.User;
import web.technologies.flixer.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getUsers(){
        return userService.getUsers();
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public boolean isUserExist(@RequestBody AuthenticationDTO authenticationDTO) {
        return userService.isUserExist(authenticationDTO);
    }
}
