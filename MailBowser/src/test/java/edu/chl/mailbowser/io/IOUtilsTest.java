package edu.chl.mailbowser.io;

import org.junit.Test;

import java.io.Closeable;
import java.io.FileInputStream;

import static org.junit.Assert.*;

/**
 * Created by mats on 11/05/15.
 */
public class IOUtilsTest {

    @Test
    public void testCloseStream() throws Exception {
        MockStream stream = new MockStream();
        IOUtils.closeStream(stream);
        assertTrue(stream.closed);

        Exception e = null;
        try {
            IOUtils.closeStream(null);
        } catch (Exception exception) {
            e = exception;
        }
        assertEquals(e, null);
    }
}