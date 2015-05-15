package edu.chl.mailbowser.io;

import java.io.Serializable;

/**
 * Created by mats on 11/05/15.
 *
 * An interface for generic classes for writing serializable objects to files.
 */
public interface IObjectWriter<T extends Serializable> {
    /**
     * Writes an object to a destination.
     *
     * @param object the object to write
     * @param destination the path to the file to write to
     * @return true if the write is successful, otherwise false
     */
    boolean write(T object, String destination);
}
