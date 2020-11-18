package com.gazprom.InforamtionSystem.service;

import com.gazprom.InforamtionSystem.enumeration.RoleName;
import com.gazprom.InforamtionSystem.enumeration.StatusName;
import com.gazprom.InforamtionSystem.exception.AppException;
import com.gazprom.InforamtionSystem.model.InformationSystem;
import com.gazprom.InforamtionSystem.model.Request;
import com.gazprom.InforamtionSystem.model.Role;
import com.gazprom.InforamtionSystem.model.User;
import com.gazprom.InforamtionSystem.payload.*;
import com.gazprom.InforamtionSystem.repository.RequestRepository;
import com.gazprom.InforamtionSystem.repository.RoleRepository;
import com.gazprom.InforamtionSystem.repository.SystemRepository;
import com.gazprom.InforamtionSystem.repository.UserRepository;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
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
    SystemRepository systemRepository;

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
        logger.debug("User is create.");
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
        List<Request> requestList = requestRepository.findAll();
        return CipherUtility.encrypt(ConverterJson.arrayConverterToJSON(
                Collections.singletonList(getFormatRequestList(requestList))),
                publicKey);
    }

    public boolean addRequest(String message){
        ApplicationRequest applicationRequest = (ApplicationRequest) CipherUtility.decrypt(message);
        User user = userRepository.findById(applicationRequest.getUserId())
                .orElseThrow(() -> new AppException("User not found."));
        InformationSystem informationSystem = systemRepository.findByTitle(applicationRequest.getSystem());
        Request request = new Request(StatusName.STATUS_SHIPPED.toString(),
                                    Timestamp.valueOf(
                                                    applicationRequest.getDate().getYear() + "-" +
                                                    applicationRequest.getDate().getMonth() + "-" +
                                                    applicationRequest.getDate().getDay() + " 00:00:00.0"),
                                    applicationRequest.getValidity(),
                                    user,
                                    informationSystem);
        requestRepository.save(request);
        logger.debug("Request is create.");
        return true;
    }

    @SneakyThrows
    public String getRequest(Long userId, String publicKey){
        User user = userRepository.findById(userId).orElseThrow(() -> new AppException("User not found."));

        return CipherUtility.encrypt(ConverterJson.arrayConverterToJSON(
                Collections.singletonList(getFormatRequestList(user.getRequestList()))),
                publicKey);
    }

    public String updateStatusRequest(Long id, String status){
        Request request = requestRepository.getById(id);
        request.setStatus(status);
        requestRepository.save(request);
        return "Status updated successfully";
    }

    private List<ApplicationResponse> getFormatRequestList(List<Request> requestList){
        List<ApplicationResponse> applicationResponseList = new ArrayList<>();
        for (Request request : requestList) {
            applicationResponseList.add(new ApplicationResponse(
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
                    new FillingData(request.getFilingDate().getDay(),
                            request.getFilingDate().getMonth(),
                            request.getFilingDate().getYear())

            ));
        }
        return applicationResponseList;
    }
}
