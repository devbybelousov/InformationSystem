package com.gazprom.InforamtionSystem.controller;

import com.gazprom.InforamtionSystem.payload.CipherRequest;
import com.gazprom.InforamtionSystem.payload.UserInfoRequest;
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
    public ResponseEntity<?> infoUser(@RequestParam(name = "id") String id,
                                      @RequestParam(name = "publicKey") String publicKey){
        return ResponseEntity.ok(userService.getUserInfo(Long.parseLong(id), publicKey));
    }

    @GetMapping("/request")
    public ResponseEntity<?> getRequests(@RequestParam (name = "id") Long userId,
                                         @RequestParam(name = "publicKey") String publicKey){
        return ResponseEntity.ok(userService.getRequest(userId, publicKey));
    }

    @PostMapping("/add/request")
    public ResponseEntity<?> addRequest(@RequestBody String message){
        return ResponseEntity.ok(userService.addRequest(message));
    }
}
