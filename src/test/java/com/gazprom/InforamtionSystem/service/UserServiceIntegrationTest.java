package com.gazprom.InforamtionSystem.service;

import com.gazprom.InforamtionSystem.enumeration.RoleName;
import com.gazprom.InforamtionSystem.model.Role;
import com.gazprom.InforamtionSystem.model.User;
import com.gazprom.InforamtionSystem.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class UserServiceIntegrationTest {

    @TestConfiguration
    static class EmployeeServiceImplTestContextConfiguration {

        @Bean
        public UserService userService() {
            return new UserService();
        }
    }

    @Autowired
    UserService userService;

    @MockBean
    UserRepository userRepository;

    @Before
    public void setUp() {
        User user = new User("admin", "admin", "admin", "admin", "admin", Collections.singleton(new Role(1, RoleName.ROLE_ADMIN.toString())));
        Mockito.when(userRepository.findByUserName("admin"))
                .thenReturn(java.util.Optional.of(user));
    }

    @Test
    public void whenValidName_thenEmployeeShouldBeFound() {
        String name = "admin";
        User found = userRepository.findByUserName(name).orElseThrow();

        assertThat(found.getName())
                .isEqualTo(name);
    }
}
