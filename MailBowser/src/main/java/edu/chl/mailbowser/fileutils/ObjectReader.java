package edu.chl.mailbowser.fileutils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

import static edu.chl.mailbowser.fileutils.IOUtils.closeStream;

/**
 * Created by mats on 11/05/15.
 *
 * A generic class for reading serializable objects from files.
 */
public class ObjectReader<T extends Serializable> {
    /**
     * Read an object from a source file.
     *
     * @param source the file to read from
     * @return the object that is read
     * @throws ObjectReadException if an error occurs while reading the object
     */
    public T read(String source) throws ObjectReadException {
        FileInputStream fileInputStream = null;
        ObjectInputStream objectInputStream = null;

        T object;

        try {
            fileInputStream = new FileInputStream(source);
            objectInputStream = new ObjectInputStream(fileInputStream);
            object = (T) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new ObjectReadException();
        } finally {
            closeStream(fileInputStream);
            closeStream(objectInputStream);
        }

        return object;
    }
}
