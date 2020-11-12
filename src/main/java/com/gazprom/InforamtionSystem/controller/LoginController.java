package com.gazprom.InforamtionSystem.controller;

import com.gazprom.InforamtionSystem.enumeration.RoleName;
import com.gazprom.InforamtionSystem.exception.AppException;
import com.gazprom.InforamtionSystem.model.Role;
import com.gazprom.InforamtionSystem.model.User;
import com.gazprom.InforamtionSystem.payload.*;
import com.gazprom.InforamtionSystem.repository.RoleRepository;
import com.gazprom.InforamtionSystem.repository.UserRepository;
import com.gazprom.InforamtionSystem.security.JwtTokenProvider;
import com.gazprom.InforamtionSystem.service.CipherUtility;
import com.gazprom.InforamtionSystem.service.ConverterJson;
import com.gazprom.InforamtionSystem.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class LoginController {
    @Autowired
    AuthenticationManager authenticationManager;

    Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;

    @Autowired
    UserService userService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody CipherRequest cipherRequest) {
        LoginRequest loginRequest = (LoginRequest)CipherUtility.decrypt(cipherRequest.getCipher());
        String username = loginRequest.getUserName();
        String password = loginRequest.getPassword();
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            username,
                            password
                    )
            );
        } catch (AuthenticationException e) {
            logger.error("Invalid username/password supplied");
            throw new BadCredentialsException("Invalid username/password supplied");
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = userRepository.findByUserName(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with username: " + username)
                );

        String jwt = tokenProvider.generateToken(authentication);
        String cipherData = "";
        logger.error(cipherRequest.getPublicKey().replaceAll("\\n", "").
                replace("-----BEGIN PUBLIC KEY-----", "").
                replace("-----END PUBLIC KEY-----", ""));
        try {
            cipherData = CipherUtility.encrypt(ConverterJson.converterToJSON(new JwtAuthenticationResponse(jwt, user.getId())), cipherRequest.getPublicKey());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(cipherData);
    }

    @GetMapping("/public/key")
    public ResponseEntity<?> getPublicKey(){
        return ResponseEntity.ok(userService.getPublicKey());
    }
}
