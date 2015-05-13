package edu.chl.mailbowser.io;

import java.io.*;

import static edu.chl.mailbowser.io.IOUtils.closeStream;

/**
 * Created by mats on 11/05/15.
 *
 * A generic class for writing serializable objects to files.
 */
public class ObjectWriter<T extends Serializable> implements IObjectWriter<T> {
    /**
     * {@inheritDoc}
     */
    @Override
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
        } catch (IOException | NullPointerException e) {
            success = false;
        } finally {
            closeStream(fileOutputStream);
            closeStream(objectOutputStream);
        }

        return success;
    }
}