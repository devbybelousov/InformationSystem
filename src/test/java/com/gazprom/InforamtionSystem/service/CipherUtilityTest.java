package com.gazprom.InforamtionSystem.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.security.KeyPair;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
public class CipherUtilityTest {

    private final Logger logger = LoggerFactory.getLogger(CipherUtilityTest.class);

    @Test
    public void cipherUtilityTest() throws Exception {
        String words = "Hello, World!";
        KeyPair keyPair = CipherUtility.generateKeyPair();
        String newWord = CipherUtility.encrypt(words, keyPair.getPublic());
        String decodedWord = CipherUtility.decrypt(newWord, keyPair.getPrivate());
        //Assert.assertEquals(words, decodedWord);
        assertThat(decodedWord)
                .isEqualTo(words);
    }
}
