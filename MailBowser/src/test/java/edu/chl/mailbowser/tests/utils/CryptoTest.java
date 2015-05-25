package edu.chl.mailbowser.tests.utils;

import edu.chl.mailbowser.utils.Crypto;
import org.junit.Test;

import static org.junit.Assert.*;

public class CryptoTest {

    @Test
    public void testDecryptEncryptDecrypt() throws Exception {
        String key = "12345678";
        String testText = "Hej";
        byte[] encrypted = Crypto.encryptString(testText, key);
        String result = Crypto.decryptByteArray(encrypted, key);
        assertEquals(testText,result);

    }
}