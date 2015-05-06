package edu.chl.mailbowser.tag.models;

import edu.chl.mailbowser.tag.models.Tag;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

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
    }

    @Test
    public void testHashCode() throws Exception {
        assertTrue(tag.hashCode() == tag.hashCode());
        assertFalse(tag.hashCode() == s.hashCode());
    }
}