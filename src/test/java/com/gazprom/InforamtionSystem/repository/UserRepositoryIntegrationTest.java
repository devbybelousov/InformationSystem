package com.gazprom.InforamtionSystem.repository;

import com.gazprom.InforamtionSystem.enumeration.RoleName;
import com.gazprom.InforamtionSystem.model.Role;
import com.gazprom.InforamtionSystem.model.User;
import com.gazprom.InforamtionSystem.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

import static org.assertj.core.api.Assertions.*;


@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryIntegrationTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void whenFindByName_thenReturnEmployee() {
        // given
        User user = new User("admin", "admin",  "admin", "admin", "admin", Collections.singleton(new Role(1, RoleName.ROLE_ADMIN.toString())));
        entityManager.persist(user);
        entityManager.flush();

        // when
        User found = userRepository.findByUserName(user.getUserName()).orElseThrow();

        // then
        assertThat(found.getUserName())
                .isEqualTo(user.getUserName());
    }
}
