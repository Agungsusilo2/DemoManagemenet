package org.example.demomanagemenet.user;

import jakarta.validation.ConstraintViolationException;
import org.example.demomanagemenet.user.model.LoginRequest;
import org.example.demomanagemenet.user.model.RegisterRequest;
import org.example.demomanagemenet.user.model.UpdateUserRequest;
import org.example.demomanagemenet.user.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void testUserRegisterError() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("admin");
        registerRequest.setPassword("admin");

        Assertions.assertThrows(ConstraintViolationException.class, () -> {
            userService.registerUser(registerRequest);
        });
    }

    @Test
    public void testUserLoginError() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("admin");
        loginRequest.setPassword("admin");
        Assertions.assertThrows(ResponseStatusException.class, () -> {
            userService.Login(loginRequest);
        });
    }

    @Test
    public void testUserUpdateError() {
        UpdateUserRequest updateUserRequest = new UpdateUserRequest();
        updateUserRequest.setUsername("admin");
        updateUserRequest.setPassword("admin1");
        updateUserRequest.setOldPassword("admin");

        Assertions.assertThrows(ResponseStatusException.class, () -> {
            userService.update("salah", updateUserRequest);
        });
    }
}
