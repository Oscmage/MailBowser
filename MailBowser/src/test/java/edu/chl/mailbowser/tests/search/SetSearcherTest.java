package edu.chl.mailbowser.tests.search;

import edu.chl.mailbowser.tag.Tag;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by mats on 05/05/15.
 */
public class SetSearcherTest {
    private Set<Tag> set;

    @Before
    public void setUp() throws Exception {

        //Creates a new HashSet
        set = new HashSet<>();

        //Adds some tags
        set.add(new Tag("abc"));
        set.add(new Tag("123"));
        set.add(new Tag("abc123"));
        set.add(new Tag("123abc"));
        set.add(new Tag("a123"));
        set.add(new Tag("1abc"));
    }

    @Test
    public void testSearch() throws Exception {
        //Set<Tag> result1 = SetSearcher.search(set, "abc");
        //assertTrue(result1.contains(new Tag("abc")));
        //assertFalse(result1.contains(new Tag("123")));
        //assertTrue(result1.contains(new Tag("abc123")));
        //assertFalse(result1.contains(new Tag("123abc")));
        //assertFalse(result1.contains(new Tag("a123")));
        //assertFalse(result1.contains(new Tag("1abc")));
        //
        //Set<Tag> result2 = SetSearcher.search(set, "1");
        //assertFalse(result2.contains(new Tag("abc")));
        //assertTrue(result2.contains(new Tag("123")));
        //assertFalse(result2.contains(new Tag("abc123")));
        //assertTrue(result2.contains(new Tag("123abc")));
        //assertFalse(result2.contains(new Tag("a123")));
        //assertTrue(result2.contains(new Tag("1abc")));
        //
        //Set<Tag> result3 = SetSearcher.search(set, "");
        //assertTrue(result3.contains(new Tag("abc")));
        //assertTrue(result3.contains(new Tag("123")));
        //assertTrue(result3.contains(new Tag("abc123")));
        //assertTrue(result3.contains(new Tag("123abc")));
        //assertTrue(result3.contains(new Tag("a123")));
        //assertTrue(result3.contains(new Tag("1abc")));
        //
        //Set<Tag> result4 = SetSearcher.search(set, null);
        //assertFalse(result4.contains(new Tag("abc")));
        //assertFalse(result4.contains(new Tag("123")));
        //assertFalse(result4.contains(new Tag("abc123")));
        //assertFalse(result4.contains(new Tag("123abc")));
        //assertFalse(result4.contains(new Tag("a123")));
        //assertFalse(result4.contains(new Tag("1abc")));
        //
        //Set<Tag> result5 = SetSearcher.search(set, "abc1234");
        //assertFalse(result5.contains(new Tag("abc")));
        //assertFalse(result5.contains(new Tag("123")));
        //assertFalse(result5.contains(new Tag("abc123")));
        //assertFalse(result5.contains(new Tag("123abc")));
        //assertFalse(result5.contains(new Tag("a123")));
        //assertFalse(result5.contains(new Tag("1abc")));
        //
        //Set<Tag> result6 = SetSearcher.search(set, "abc123");
        //assertFalse(result6.contains(new Tag("abc")));
        //assertFalse(result6.contains(new Tag("123")));
        //assertTrue(result6.contains(new Tag("abc123")));
        //assertFalse(result6.contains(new Tag("123abc")));
        //assertFalse(result6.contains(new Tag("a123")));
        //assertFalse(result6.contains(new Tag("1abc")));
    }
}