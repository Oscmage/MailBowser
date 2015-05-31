package edu.chl.mailbowser.tests.tag.models;

import edu.chl.mailbowser.tag.Tag;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by OscarEvertsson on 30/04/15.
 * A test class for Tag.
 */
public class TagTest {
    private final String s = "Work";
    private Tag tag;

    @Before
    public void initialize() {
        tag = new Tag(s);
    }

    /**
     * Indirectly tests setTag since the constructor uses setTag to set the name for the new tag.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testConstructor() {
        //Tests IllegalArgument being thrown
        Tag t = new Tag(null);
    }

    @Test
    public void testEquals() throws Exception {
        //Tests if same object returns true
        assertTrue(tag.equals(tag));

        //Tests if null return false
        assertFalse(tag.equals(null));

        //Tests if a tag equals a new object
        assertFalse(tag.equals(new Object()));

        //Tests if a tag equals the int 10
        assertFalse(tag.equals(10));

        //Tests if a tag equals another tag if they got the same name
        assertTrue(tag.equals(new Tag(s)));
        assertTrue(tag.equals(new Tag("Work")));

        //The string is made lowercase on start (That's why this should be true)
        assertTrue(tag.equals(new Tag("work")));

        //Tests if a tag equals a string with the same name as the tag
        assertFalse(tag.equals(s));
    }

    @Test
    public void testHashCode() throws Exception {
        // Tests if hashCode returns the same value with the same object
        assertTrue(tag.hashCode() == tag.hashCode());

        /* Tests if hashCode returns the same value for the tag and
         * a string with the same name as the tag.
        */
        assertFalse(tag.hashCode() == s.hashCode());
    }

    /**
     * This test will check whether the "hashCode => equals" is done correctly.
     */
    @Test
    public void testEqualsAndHashCodeRelationship(){
        //Hashcode true => Equals should also be true
        assertTrue(tag.hashCode() == tag.hashCode() && tag.equals(tag));
    }

    /**
     * The matches method expects the query to be a start of the tags name (See String "startsWith" method.)
     * and not null.
     * @throws Exception
     */
    @Test
    public void testMatches() throws Exception {
        //Empty string
        assertTrue(tag.matches(""));

        //part of the name but not startsWith
        assertFalse(tag.matches("ork"));

        // Null test
        assertFalse(tag.matches(null));

        assertTrue(tag.matches("w"));

        //startsWith "W" should be converted to lowercase.
        assertTrue(tag.matches("W"));

        //Same string but lowerCase
        assertTrue(tag.matches("work"));

        //Same string
        assertTrue(tag.matches("Work"));

        //String backwards
        assertFalse(tag.matches("Krow"));

        assertFalse(tag.matches("workasdf"));
        assertFalse(tag.matches("Workasdf"));
    }

    @Test
    public void testCompareTo() throws Exception {
        assertTrue(tag.compareTo(new Tag("A"))>0);
        assertTrue(tag.compareTo(new Tag("x"))<0);
         //Same string should return true
        assertTrue(tag.compareTo(new Tag(s)) == 0);
        //Not the same string should return false
        assertFalse(tag.compareTo(new Tag("uohqwoie")) == 0);
    }
}