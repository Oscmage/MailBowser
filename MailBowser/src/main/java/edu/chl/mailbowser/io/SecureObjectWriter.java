package edu.chl.mailbowser.io;

import com.sun.xml.internal.bind.v2.TODO;

import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SealedObject;
import java.io.*;
import java.security.*;

import static edu.chl.mailbowser.io.IOUtils.closeStream;

/**
 * Created by jesper on 2015-05-23.
 */
public class SecureObjectWriter<T extends Serializable> extends ObjectWriter<T> {
    @Override
    public boolean write(T object, String destination){
        File fileIn;
        FileInputStream fileInputStream;
        ObjectInputStream objectInputStream;

        PrivateKey privateKey = null;
        PublicKey publicKey = null;

        FileOutputStream fileOutputStream = null;
        ObjectOutputStream objectOutputStream = null;

        boolean success = true;

        try {
            //writes object to disk
            fileOutputStream = new FileOutputStream(destination);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);

            objectOutputStream.writeObject(object);

            //reads the written file, encrypts and overwrites it
            fileIn = new File(destination);
            fileInputStream = new FileInputStream(fileIn);
            byte [] byteStream = new byte [(int) fileIn.length()];
            fileInputStream.read(byteStream);
                //Generate keys to encrypt
            KeyPairGenerator keyPairGenerator = null;
            KeyPair keyPair;
            try {
                keyPairGenerator = KeyPairGenerator.getInstance("DSA", "SUN");
            } catch (NoSuchAlgorithmException e) {
                //TODO: Handel
                e.printStackTrace();
            } catch (NoSuchProviderException e) {
                //TODO: Handel
                e.printStackTrace();
            }
            if (keyPairGenerator != null) {
                keyPair = keyPairGenerator.generateKeyPair();
                privateKey = keyPair.getPrivate();
                publicKey = keyPair.getPublic();
            }
            Cipher cipher = null;
            try {
                cipher = Cipher.getInstance("DSA");
            } catch (NoSuchAlgorithmException e) {
                //TODO: Handel
                e.printStackTrace();
            } catch (NoSuchPaddingException e) {
                //TODO: Handel
                e.printStackTrace();
            }

            if (publicKey != null){
                try {
                    cipher.init(Cipher.ENCRYPT_MODE, publicKey);
                } catch (InvalidKeyException e) {
                    //TODO: Handel
                    e.printStackTrace();
                }
            }

            try {
                SealedObject encryptedObject = new SealedObject(byteStream,cipher);
            } catch (IllegalBlockSizeException e) {
                //TODO:Handel
                e.printStackTrace();
            }

            fileOutputStream.close();
            objectOutputStream.close();
        } catch (IOException | NullPointerException e) {
            success = false;
        } finally {
            closeStream(fileOutputStream);
            closeStream(objectOutputStream);
        }

        return success;
    }
}
