package gov.cdc.nbs.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import gov.cdc.nbs.model.LoginResponse;
import gov.cdc.nbs.service.UserService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
public class UserController {
    private final UserService userService;

    @PostMapping("/login")
    public LoginResponse login(String username, String password) {
        return new LoginResponse(username, userService.loadUserByUsername(username).getToken());
    }

}
