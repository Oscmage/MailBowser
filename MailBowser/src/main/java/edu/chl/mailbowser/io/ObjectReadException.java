package edu.chl.mailbowser.io;

/**
 * Created by mats on 11/05/15.
 */
public class ObjectReadException extends Exception {
    public ObjectReadException() {}

    public ObjectReadException(String msg) {
        super(msg);
    }

    public ObjectReadException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public ObjectReadException(Throwable cause) {
        super(cause);
    }
}
