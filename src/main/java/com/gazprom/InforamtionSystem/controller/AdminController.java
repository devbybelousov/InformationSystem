package com.gazprom.InforamtionSystem.controller;

import com.gazprom.InforamtionSystem.payload.UserRequest;
import com.gazprom.InforamtionSystem.service.CipherUtility;
import com.gazprom.InforamtionSystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/admin")
public class AdminController {

    @Autowired
    UserService userService;

    @PostMapping("/create/user")
    public ResponseEntity<?> registerUser(@RequestParam String cipherText) {
        UserRequest signUpRequest = (UserRequest) CipherUtility.decrypt(cipherText);
        return userService.createUser(signUpRequest);
    }
}
