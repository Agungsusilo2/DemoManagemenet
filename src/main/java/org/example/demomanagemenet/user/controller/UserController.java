package org.example.demomanagemenet.user.controller;

import org.example.demomanagemenet.barang.model.ListBarangRequest;
import org.example.demomanagemenet.user.entity.User;
import org.example.demomanagemenet.user.model.*;
import org.example.demomanagemenet.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping(path = "/api/user/register",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    private WebResponse<String> register(@RequestBody RegisterRequest registerRequest){
        userService.registerUser(registerRequest);
        return WebResponse.<String>builder().data("success").build();
    }

    @PostMapping(path = "/api/user/auth",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    private WebResponse<String> login(@RequestBody LoginRequest loginRequest){
        String logined = userService.Login(loginRequest);
        return WebResponse.<String>builder().data(logined).build();
    }

    @GetMapping(path = "/api/users")
    private WebResponse<List<UserResponse>> getAllUsers(@RequestParam(name = "page",defaultValue = "0")int page, @RequestParam(name = "size",defaultValue = "15")int size){
        List<UserResponse> all = userService.getAll(new ListBarangRequest(page, size));
        return WebResponse.<List<UserResponse>>builder().data(all).build();
    }

    @DeleteMapping(path = "/api/users/{userId}")
    private WebResponse<String> delete(@PathVariable("userId") String userId){
        userService.delete(userId);
        return WebResponse.<String>builder().data("success").build();
    }

    @GetMapping("/api/users/{userId}")
    private WebResponse<UserResponse> getUser(@PathVariable("userId") String userId){
        UserResponse userResponse = userService.getById(userId);
        return WebResponse.<UserResponse>builder().data(userResponse).build();
    }
    @PutMapping("/api/users/{userId}")
    private WebResponse<String> update(@PathVariable(name = "userId") String id,@RequestBody UpdateUserRequest updateUserRequest){
        userService.update(id,updateUserRequest);
        return WebResponse.<String>builder().data("success").build();
    }

    @GetMapping("/api/users/search")
    private WebResponse<List<UserResponse>> search(@RequestParam(name = "keyword")String keyword,@RequestParam(name = "page",defaultValue = "0")int page, @RequestParam(name = "size",defaultValue = "15")int size){
        Page<UserResponse> searched = userService.search(new ListUserRequest(page, size), keyword);
        return WebResponse.<List<UserResponse>>builder().data(searched.getContent().stream().toList()).build();
    }
}
