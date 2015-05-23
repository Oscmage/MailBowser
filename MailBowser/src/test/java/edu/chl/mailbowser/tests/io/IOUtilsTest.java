package edu.chl.mailbowser.tests.io;

import edu.chl.mailbowser.io.IOUtils;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by mats on 11/05/15.
 */
public class IOUtilsTest {

    @Test
    public void testCloseStream() throws Exception {
        MockStream stream = new MockStream();
        IOUtils.closeStream(stream);
        assertTrue(stream.closed); //Did stream really close?

        Exception e = null;
        try {
            IOUtils.closeStream(null);
        } catch (Exception exception) {
            e = exception;
        }
        assertEquals(e, null);
    }
}