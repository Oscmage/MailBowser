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

        boolean exceptionThrown = false;
        try {
            encrypted = Crypto.encryptString(testText,"dfghjklölkuytredfvbn");
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        assertTrue(exceptionThrown);
        exceptionThrown = false;
        try {
            result = Crypto.decryptByteArray(new byte[]{1,4,2,64,3,4,2}, key);
        } catch (Exception e) {
            exceptionThrown = true;
        }
        assertTrue(exceptionThrown);
        try {
            Crypto.encryptString(null,key);
        } catch (Exception e) {
            exceptionThrown = true;
        }
        assertTrue(exceptionThrown);
    }
}