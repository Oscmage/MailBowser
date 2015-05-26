package edu.chl.mailbowser.io;

/**
 * Created by mats on 11/05/15.
 *
 * An exception for when something goes wrong when reading objects from file.
 */
public class ObjectReadException extends Exception {
    /**
     * Creates an empty ObjectReadException
     */
    public ObjectReadException() {}

    /**
     * Creates an ObjectReadException from an existing exception.
     *
     * @param e the exception to create this exception from
     */
    public ObjectReadException(Exception e) {
        super(e);
    }
}
