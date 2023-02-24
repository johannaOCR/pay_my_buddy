package com.paymybuddy.PayMyBuddy.model;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;



@ExtendWith(MockitoExtension.class)
public class UserTest {
    private static final Logger logger = LogManager.getLogger("UserTest");

    @Mock
    User user;

    @Test
    public void testToStringUser(){
        assertNotNull(user);

    }
}
