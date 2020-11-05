package com.gazprom.InforamtionSystem.controller;

import com.gazprom.InforamtionSystem.payload.UserResponse;
import com.gazprom.InforamtionSystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/info")
    public String infoUser(@RequestParam(value = "id") Long id, @RequestParam(value = "publicKey") String publicKey){
        return userService.getUserInfo(id, publicKey);
    }

    @GetMapping("/public/key")
    public String getPublicKey(){
        return userService.getPublicKey();
    }
}
