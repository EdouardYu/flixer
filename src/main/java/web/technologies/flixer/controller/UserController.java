package web.technologies.flixer.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import web.technologies.flixer.dto.*;
import web.technologies.flixer.entity.User;
import web.technologies.flixer.service.UserService;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {
    private final UserService userService;

    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<User> getUsers(Pageable pageable){
        return userService.getUsers(pageable);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDTO getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/{id}/history")
    public List <HistoryUserDTO> getHistoryByUserId(@PathVariable Long id) {
        return userService.getHistoryByUserId(id);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public UserDTO modifyUser(@PathVariable Long id, @Valid @RequestBody UserModificationDTO userDTO) {
        return this.userService.modifyUser(id, userDTO);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @PostMapping(path = "/{id}/credit", consumes = MediaType.APPLICATION_JSON_VALUE)
    public UserDTO creditAccount(@PathVariable Long id, @Valid @RequestBody CreditAccountDTO userDTO) {
        return this.userService.creditAccount(id, userDTO);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @PutMapping(path = "/{id}/password", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void modifyPassword(@PathVariable Long id, @Valid @RequestBody PasswordModificationDTO userDTO) {
        this.userService.modifyPassword(id, userDTO);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @PostMapping(path = "/{id}/subscribe", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDTO subscribe(@PathVariable Long id) {
        return this.userService.subscribe(id);
    }
}
