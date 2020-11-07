package com.gazprom.InforamtionSystem.controller;

import com.gazprom.InforamtionSystem.payload.UserInfoRequest;
import com.gazprom.InforamtionSystem.payload.UserResponse;
import com.gazprom.InforamtionSystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/info")
    public ResponseEntity<?> infoUser(@RequestBody UserInfoRequest userInfoRequest){
        return ResponseEntity.ok(userService.getUserInfo(Long.parseLong(userInfoRequest.getId()), userInfoRequest.getPublicKey()));
    }
}
