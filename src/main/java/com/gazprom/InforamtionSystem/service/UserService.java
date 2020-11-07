package com.gazprom.InforamtionSystem.service;

import com.gazprom.InforamtionSystem.enumeration.RoleName;
import com.gazprom.InforamtionSystem.exception.AppException;
import com.gazprom.InforamtionSystem.model.Role;
import com.gazprom.InforamtionSystem.model.User;
import com.gazprom.InforamtionSystem.payload.ApiResponse;
import com.gazprom.InforamtionSystem.payload.UserRequest;
import com.gazprom.InforamtionSystem.payload.UserResponse;
import com.gazprom.InforamtionSystem.repository.RoleRepository;
import com.gazprom.InforamtionSystem.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Collections;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    private final Logger logger = LoggerFactory.getLogger(UserService.class);

    public ResponseEntity<?> createUser(UserRequest userRequest){
        if (userRepository.existsByUserName(userRequest.getUserName())) {
            return new ResponseEntity(new ApiResponse(false, "Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }

        // Creating user's account
        Role userRole = roleRepository.findByRole(String.valueOf(RoleName.ROLE_EMPLOYEE))
                .orElseThrow(() -> new AppException("User Role not set."));

        User user = new User(userRequest.getUserName(), userRequest.getPassword(),
                            userRequest.getName(), userRequest.getLastName(),
                            userRequest.getMiddleName(), Collections.singleton(userRole));

        user.setPassword(passwordEncoder.encode(user.getPassword()));


        user.setRoles(Collections.singleton(userRole));
        userRepository.save(user);
        return ResponseEntity.ok(new ApiResponse(true, "User registered successfully"));
    }

    public String getUserInfo(Long id, String key){
        User user = userRepository.findById(id).orElseThrow();
        String userResponse = ConverterJson
                .converterToJSON(new UserResponse(user.getUserName(),
                                                user.getName(),
                                                user.getName(),
                                                user.getMiddleName(),
                                                user.getRoles().iterator().next(),
                                                user.getDepartment()));
        String encodeUser = "";
        logger.info(userResponse);
        try {
            encodeUser = CipherUtility.encrypt(userResponse, key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encodeUser;
    }

    public String getPublicKey(){
        String publicKey = "";
        try {
            KeyPair keyPair = CipherUtility.getKeyPairFromKeyStore();
            publicKey = keyPair.getPublic().toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        logger.info("Public key: {}", publicKey);

        return publicKey;
    }
}
