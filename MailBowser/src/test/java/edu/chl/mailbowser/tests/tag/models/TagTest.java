package edu.chl.mailbowser.tests.tag.models;

import edu.chl.mailbowser.tag.Tag;
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
        assertTrue(tag.equals(tag)); //Tests if same object returns true
        assertFalse(tag.equals(null)); //Tests if null return false
        assertFalse(tag.equals(new Object())); //Tests if a tag equals a new object
        assertFalse(tag.equals(10)); //Tests if a tag equals the int 10
        assertTrue(tag.equals(new Tag(s))); //Tests if a tag equals another tag if they got the same name
        assertFalse(tag.equals(s)); //Tests if a tag equals a string with the same name as the tag
    }

    @Test
    public void testHashCode() throws Exception {
        assertTrue(tag.hashCode() == tag.hashCode()); // Tests if hashCode returns the same value with the same object
        assertFalse(tag.hashCode() == s.hashCode()); // Tests if hashCode returns the same value for the tag and
                                                    // a string with the same name as the tag.
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