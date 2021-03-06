package edu.chl.mailbowser.utils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.UnsupportedCharsetException;
import java.nio.charset.spi.CharsetProvider;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

/**
 * Created by jesper on 2015-05-25.
 *
 * A class for dealing with encryption and decryption of strings and byte arrays. This class only contains static
 * methods and can not be instantiated by other classes.
 */
public class Crypto {
    //Private constructor to prevent instantiation
    private Crypto(){}

    /**
     * Encrypts a String using DES algorithem
     * @param str the string to be encrypted
     * @param keyString the key used to encrypted
     * @return the encrypted byte array
     */
    public static byte[] encryptString(String str, String keyString, String charset) {
        try {
            byte[] keyBytes;
            keyBytes = keyString.getBytes(charset);
            Key key = new SecretKeySpec(keyBytes, "DES");

            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return cipher.doFinal(str.getBytes(charset));
        } catch (IllegalBlockSizeException | InvalidKeyException |
                BadPaddingException | NoSuchAlgorithmException |
                NoSuchPaddingException | UnsupportedEncodingException e) {

            throw new IllegalArgumentException(e.getCause());
        }
    }

    /**
     * Decrypts a byte array to a string using DES algorithem
     * @param byteArray to be decrypted
     * @param keyString key to used decrypting
     * @return decrypted string
     */
    public static String decryptByteArray(byte[] byteArray, String keyString, String charset) {
        try {
            byte[] keyBytes = keyString.getBytes(charset);
            Key key = new SecretKeySpec(keyBytes, "DES");
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, key);

            return new String(cipher.doFinal(byteArray), charset);
        } catch (IllegalBlockSizeException | InvalidKeyException |
                BadPaddingException | NoSuchAlgorithmException |
                NoSuchPaddingException | UnsupportedEncodingException e) {

            throw new IllegalArgumentException(e);
        }
    }
}
