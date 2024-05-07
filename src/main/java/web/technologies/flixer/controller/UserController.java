package web.technologies.flixer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import web.technologies.flixer.dto.AuthenticationDTO;
import web.technologies.flixer.dto.HistoryUserDTO;
import web.technologies.flixer.dto.SignUpDTO;
import web.technologies.flixer.entity.User;
import web.technologies.flixer.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {
    private final UserService userService;

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

    @PostMapping(value = "/signup", consumes = MediaType.APPLICATION_JSON_VALUE)
    public boolean isUserCreated(@RequestBody SignUpDTO signUpDTO) {
        return userService.isUserCreated(signUpDTO);
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/{id}/history")
    public List <HistoryUserDTO> getHistoryByUserId(@PathVariable Long id) {
        return userService.getHistoryByUserId(id);
    }
}
