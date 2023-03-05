package com.paymybuddy.PayMyBuddy.integration;

import com.paymybuddy.PayMyBuddy.PayMyBuddyApplication;
import com.paymybuddy.PayMyBuddy.model.User;
import com.paymybuddy.PayMyBuddy.repository.UserRepository;
import com.paymybuddy.PayMyBuddy.service.UserService;
import junit.framework.Assert;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
public class UserServiceIntegrationTest {
    private static final Logger logger = LogManager.getLogger("UserTest");
    @TestConfiguration
    static class UserServiceIntegrationTestContextConfiguration {
        @Bean
        public UserService userService() {
            return new UserService();
        }
    }

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;
/*
    @Before
    public void setUp() {
        User userTest = new User();
        userTest.setFirstname("UserTest");
        userTest.setUserId(1);
        Optional<User> userOptional = Optional.ofNullable(userTest);

            Mockito.when(userRepository.findById(userTest.getUserId()))
                    .thenReturn(userOptional);

    }

    @Test
    public void findUserByIdTest() {
        Integer id = 1;
        User found = new User();
        if(userService.getUserById(id).isPresent()){
             found = userService.getUserById(id).get();
        };
        logger.info("user found " + found.toString());
        Assert.assertEquals(found.getUserId(), (int) id);
    }
    @Test
    public void findUserByIdWithByIdTest() {
        Integer id = 2;
        User found = new User();
        if(userService.getUserById(id).isPresent()){
            found = userService.getUserById(id).get();
        };
        logger.info("user found " + found.toString());
        Assert.assertNotSame(found.getUserId(), (int) id);
    }
*/
}
