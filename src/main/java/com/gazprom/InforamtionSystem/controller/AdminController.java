package com.gazprom.InforamtionSystem.controller;

import com.gazprom.InforamtionSystem.payload.ApiResponse;
import com.gazprom.InforamtionSystem.payload.UserRequest;
import com.gazprom.InforamtionSystem.service.CipherUtility;
import com.gazprom.InforamtionSystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
        if(userService.createUser(signUpRequest))
            return new ResponseEntity(new ApiResponse(false, "Username is already taken!"),
                HttpStatus.BAD_REQUEST);
        return ResponseEntity.ok(new ApiResponse(true, "User registered successfully"));
    }

    @GetMapping("/all/request")
    public ResponseEntity<?> getAllRequest(@RequestParam(name = "publicKey") String publicKey){
        return ResponseEntity.ok(userService.getAllRequest(publicKey));
    }

    @GetMapping("update/status")
    public ResponseEntity<?> updateStatusRequest(@RequestParam(name = "requestId") Long id,
                                                 @RequestParam(name = "newStatus") String status){
        return ResponseEntity.ok(userService.updateStatusRequest(id, status));
    }
}
