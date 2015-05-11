package edu.chl.mailbowser.io;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

import static edu.chl.mailbowser.io.IOUtils.closeStream;

/**
 * Created by mats on 11/05/15.
 *
 * A generic class for reading serializable objects from files.
 */
public class ObjectReader<T extends Serializable> implements IObjectReader<T> {
    /**
     * {@inheritDoc}
     */
    @Override
    public T read(String source) throws ObjectReadException {
        FileInputStream fileInputStream = null;
        ObjectInputStream objectInputStream = null;

        T object = null;

        try {
            fileInputStream = new FileInputStream(source);
            objectInputStream = new ObjectInputStream(fileInputStream);
            object = (T) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException | NullPointerException e) {
            throw new ObjectReadException();
        } finally {
            closeStream(fileInputStream);
            closeStream(objectInputStream);
        }

        return object;
    }
}
