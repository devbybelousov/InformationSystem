package com.gazprom.InforamtionSystem.service;

import com.gazprom.InforamtionSystem.enumeration.RoleName;
import com.gazprom.InforamtionSystem.enumeration.StatusName;
import com.gazprom.InforamtionSystem.exception.AppException;
import com.gazprom.InforamtionSystem.model.*;
import com.gazprom.InforamtionSystem.payload.*;
import com.gazprom.InforamtionSystem.repository.*;
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
    UnitRepository unitRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    private final Logger logger = LoggerFactory.getLogger(UserService.class);

    public boolean createUser(UserRequest userRequest){
        if (userRepository.existsByUserName(userRequest.getUserName())) {
            return false;
        }

        Role userRole = roleRepository.findById(userRequest.getRoleId())
                .orElseThrow(() -> new AppException("User Role not set."));
        Department department = departmentRepository.findById(userRequest.getDepartmentId())
                .orElseThrow(() -> new AppException(("User Department not set.")));


        User user = new User(userRequest.getUserName(), userRequest.getPassword(),
                            userRequest.getName(), userRequest.getLastName(),
                            userRequest.getMiddleName(), Collections.singleton(userRole), department);

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

    @SneakyThrows
    public String getAllUnit(String publicKey){
        List<Unit> units = unitRepository.findAll();
        return CipherUtility.encrypt(ConverterJson.arrayConverterToJSON(units), publicKey);
    }

    @SneakyThrows
    public String getAllDepartment(String publicKey){
        List<Department> departments = departmentRepository.findAll();
        return CipherUtility.encrypt(ConverterJson.arrayConverterToJSON(departments), publicKey);
    }

    @SneakyThrows
    public String getAllRole(String publicKey){
        List<Role> roleList = roleRepository.findAll();
        return CipherUtility.encrypt(ConverterJson.arrayConverterToJSON(roleList), publicKey);
    }

    public String createSystem(String message){
        String titleSystem = (String) CipherUtility.decrypt(message);
        InformationSystem informationSystem = new InformationSystem();
        informationSystem.setTitle(titleSystem);
        systemRepository.save(informationSystem);
        return "System is create.";
    }

    public String createUnit(String message){
        String titleUnit = (String) CipherUtility.decrypt(message);
        Unit unit = new Unit();
        unit.setTitle(titleUnit);
        unitRepository.save(unit);
        return "Unit is create.";
    }

    public String createDepartment(String message){
        DepartmentRequest departmentRequest = (DepartmentRequest) CipherUtility.decrypt(message);
        Unit unit = unitRepository.findById(departmentRequest.getUnitId()).orElseThrow();
        Department department = new Department();
        department.setTitle(departmentRequest.getTitle());
        department.setUnit(unit);
        departmentRepository.save(department);
        return "Department is create.";
    }

    @SneakyThrows
    public String getAllSystem(String publicKey){
        List<InformationSystem> systemList = systemRepository.findAll();
        return CipherUtility.encrypt(ConverterJson.arrayConverterToJSON(systemList), publicKey);
    }
}
