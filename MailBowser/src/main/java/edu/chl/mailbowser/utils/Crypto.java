package edu.chl.mailbowser.utils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

/**
 * Created by jesper on 2015-05-25.
 */
public class Crypto {
    //Private constructor to prevent instantiation
    private Crypto(){}

    public static byte[] encryptString(String str, String keyString) {
        byte[] keyBytes = keyString.getBytes();
        Key key = new SecretKeySpec(keyBytes, "DES");

        try {
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return cipher.doFinal(str.getBytes());
        } catch (IllegalBlockSizeException | InvalidKeyException |
                BadPaddingException | NoSuchAlgorithmException |
                NoSuchPaddingException e) {
            throw new IllegalArgumentException(e.getCause());
        }
    }

    public static String decryptByteArray(byte[] byteArray, String keyString) {
        byte[] keyBytes = keyString.getBytes();
        Key key = new SecretKeySpec(keyBytes, "DES");

        try {
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return new String(cipher.doFinal(byteArray));
        } catch (IllegalBlockSizeException | InvalidKeyException |
                BadPaddingException | NoSuchAlgorithmException |
                NoSuchPaddingException e) {
            throw new IllegalArgumentException(e.getCause());
        }
    }
}
