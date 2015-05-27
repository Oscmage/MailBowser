package edu.chl.mailbowser.utils.io;

import java.io.Serializable;

/**
 * Created by mats on 11/05/15.
 *
 * An interface for generic classes that reads serializable objects from files.
 */
public interface IObjectReader<T extends Serializable> {
    /**
     * Read an object from a source file.
     *
     * @param source the source file to read from
     * @return the object that is read
     * @throws ObjectReadException if an error occurs while reading the object
     */
    T read(String source) throws ObjectReadException;
}
