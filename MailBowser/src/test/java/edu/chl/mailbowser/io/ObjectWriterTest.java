package edu.chl.mailbowser.io;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by mats on 11/05/15.
 */
public class ObjectWriterTest {

    ObjectWriter<String> writer = null;

    @Before
    public void setUp() throws Exception {
        writer = new ObjectWriter<>();
    }

    @Test
    public void testWrite() throws Exception {
        assertFalse(writer.write("String", null));
        assertFalse(writer.write(null, null));
    }
}