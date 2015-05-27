package edu.chl.mailbowser.tests.io;

import edu.chl.mailbowser.utils.io.ObjectReadException;
import edu.chl.mailbowser.utils.io.ObjectReader;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by mats on 11/05/15.
 */
public class ObjectReaderTest {
    ObjectReader<String> reader = null;

    @Before
    public void setUp() throws Exception {
        reader = new ObjectReader<>();
    }

    @Test
    public void testRead() throws Exception {
        Exception e = null;
        try {
            reader.read(null);
        } catch (ObjectReadException exception) {
            e = exception;
        }
        assertNotEquals(e, null);

        e = null;
        try {
            reader.read("fileThatDoesNotExist");
        } catch (ObjectReadException exception) {
            e = exception;
        }
        assertNotEquals(e, null);
    }
}