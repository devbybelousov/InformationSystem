package com.gazprom.InforamtionSystem.service;

import com.gazprom.InforamtionSystem.enumeration.RoleName;
import com.gazprom.InforamtionSystem.exception.AppException;
import com.gazprom.InforamtionSystem.model.Request;
import com.gazprom.InforamtionSystem.model.Role;
import com.gazprom.InforamtionSystem.model.User;
import com.gazprom.InforamtionSystem.payload.ApiResponse;
import com.gazprom.InforamtionSystem.payload.ApplicationResponse;
import com.gazprom.InforamtionSystem.payload.UserRequest;
import com.gazprom.InforamtionSystem.payload.UserProfile;
import com.gazprom.InforamtionSystem.repository.RequestRepository;
import com.gazprom.InforamtionSystem.repository.RoleRepository;
import com.gazprom.InforamtionSystem.repository.UserRepository;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.KeyPair;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    RequestRepository requestRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    private final Logger logger = LoggerFactory.getLogger(UserService.class);

    public boolean createUser(UserRequest userRequest){
        if (userRepository.existsByUserName(userRequest.getUserName())) {
            return false;
        }

        Role userRole = roleRepository.findByRole(String.valueOf(RoleName.ROLE_EMPLOYEE))
                .orElseThrow(() -> new AppException("User Role not set."));

        User user = new User(userRequest.getUserName(), userRequest.getPassword(),
                            userRequest.getName(), userRequest.getLastName(),
                            userRequest.getMiddleName(), Collections.singleton(userRole));

        user.setPassword(passwordEncoder.encode(user.getPassword()));


        user.setRoles(Collections.singleton(userRole));
        userRepository.save(user);
        return true;
    }

    public String getUserInfo(Long id, String key){
        User user = userRepository.findById(id).orElseThrow();
        String userProfile = ConverterJson
                .converterToJSON(new UserProfile(user.getId(),
                                                user.getUserName(),
                                                user.getName(),
                                                user.getName(),
                                                user.getMiddleName(),
                                                user.getRoles().iterator().next(),
                                                user.getDepartment()));
        String encodeUser = "";
        try {
            encodeUser = CipherUtility.encrypt(userProfile, key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encodeUser;
    }

    public String getPublicKey(){
        return CipherUtility.getPublicKey();
    }

    @SneakyThrows
    public String getAllRequest(String publicKey){
        List<Request> requestList = requestRepository.getAll();
        List<ApplicationResponse> applicationResponses = new ArrayList<>();
        for (Request request : requestList){
            applicationResponses.add(new ApplicationResponse(
                    request.getId(),
                    new UserProfile(request.getUser().getId(),
                                    request.getUser().getUserName(),
                                    request.getUser().getName(),
                                    request.getUser().getLastName(),
                                    request.getUser().getMiddleName(),
                                    request.getUser().getRoles().iterator().next(),
                                    request.getUser().getDepartment()),
                    request.getInformationSystem().getTitle(),
                    request.getValidity(),
                    request.getStatus(),
                    request.getFilingDate()
            ));
        }
        return CipherUtility.encrypt(ConverterJson.arrayConverterToJSON(Collections.singletonList(applicationResponses)), publicKey);
    }
}
