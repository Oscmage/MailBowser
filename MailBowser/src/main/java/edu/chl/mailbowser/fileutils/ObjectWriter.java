package edu.chl.mailbowser.fileutils;

import edu.chl.mailbowser.account.models.Account;

import java.io.*;

import static edu.chl.mailbowser.fileutils.IOUtils.closeStream;

/**
 * Created by mats on 11/05/15.
 *
 * A generic class for writing serializable objects to files.
 */
public class ObjectWriter<T extends Serializable> {
    /**
     * Writes an object to a destination.
     *
     * @param object the object to write
     * @param destination the path to the file to write to
     * @return true if the write is successful, otherwise false
     */
    public boolean write(T object, String destination) {
        FileOutputStream fileOutputStream = null;
        ObjectOutputStream objectOutputStream = null;

        boolean success = true;

        try {
            fileOutputStream = new FileOutputStream(destination);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);

            objectOutputStream.writeObject(object);

            fileOutputStream.close();
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            success = false;
        } finally {
            closeStream(fileOutputStream);
            closeStream(objectOutputStream);
        }

        return success;
    }
}
