package edu.chl.mailbowser.fileutils;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by mats on 11/05/15.
 */
public class IOUtils {
    private IOUtils() {} // private constructor to prevent instantiation

    /**
     * Closes a closeable stream.
     *
     * @param stream the stream to close
     */
    public static void closeStream(Closeable stream) {
        if (stream != null) {
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
