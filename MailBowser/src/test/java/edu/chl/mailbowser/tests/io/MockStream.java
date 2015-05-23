package edu.chl.mailbowser.tests.io;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;

/**
 * Created by mats on 11/05/15.
 */
public class MockStream implements Closeable {
    public boolean closed = false;

    @Override
    public void close() throws IOException {
        closed = true;
    }
}
