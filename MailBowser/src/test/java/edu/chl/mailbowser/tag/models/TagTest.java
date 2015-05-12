package edu.chl.mailbowser.tag.models;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by OscarEvertsson on 30/04/15.
 */
public class TagTest {
    private final String s = "Work";
    private Tag tag;

    @Before
    public void initialize() {
        tag = new Tag(s);
    }

    @Test
    public void testEquals() throws Exception {
        assertTrue(tag.equals(tag));
        assertFalse(tag.equals(null));
        assertFalse(tag.equals(new Object()));
        assertFalse(tag.equals(10));
        assertTrue(tag.equals(new Tag(s)));
        assertFalse(tag.equals(s));
    }

    @Test
    public void testHashCode() throws Exception {
        assertTrue(tag.hashCode() == tag.hashCode());
        assertFalse(tag.hashCode() == s.hashCode());
    }

    @Test
    public void testMatches() throws Exception {
        assertTrue(tag.matches(""));
        assertFalse(tag.matches("ork"));
        assertFalse(tag.matches(null));
        assertTrue(tag.matches("W"));
        assertTrue(tag.matches("w"));
        assertTrue(tag.matches("work"));
        assertTrue(tag.matches("Work"));
        assertFalse(tag.matches("workasdf"));
        assertFalse(tag.matches("Workasdf"));
    }
}