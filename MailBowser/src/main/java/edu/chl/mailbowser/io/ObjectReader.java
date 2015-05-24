package edu.chl.mailbowser.io;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

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
            throw new ObjectReadException(e.getMessage(), e.getCause());
        } finally {
            IOUtils.closeStream(fileInputStream);
            IOUtils.closeStream(objectInputStream);
        }

        return object;
    }
}
