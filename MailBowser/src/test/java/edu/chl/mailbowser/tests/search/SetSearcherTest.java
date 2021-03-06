package edu.chl.mailbowser.tests.search;

import edu.chl.mailbowser.tests.mock.MockSearchable;
import edu.chl.mailbowser.utils.search.SetSearcher;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by mats on 05/05/15.
 */
public class SetSearcherTest {

    private SetSearcher<MockSearchable> setSearcher = new SetSearcher<>();

    @Before
    public void setUp() throws Exception {
        setSearcher = new SetSearcher<>();
    }

    @Test
    public void testSearch() throws Exception {
        // test if the search method gets called in all searchables, and that the size of the result set is the same
        // as the search set when all searchables matches the query
        Set<MockSearchable> set1 = new HashSet<>();
        MockSearchable s1 = new MockSearchable();
        MockSearchable s2 = new MockSearchable();
        MockSearchable s3 = new MockSearchable();
        set1.add(s1);
        set1.add(s2);
        set1.add(s3);

        Set<MockSearchable> result1 = setSearcher.search(set1, "");
        assertEquals(result1.size(), 3);
        assertTrue(s1.matchesCalled);
        assertTrue(s2.matchesCalled);
        assertTrue(s3.matchesCalled);

        // test if the search method gets called in all searchables, and that the size of the result set is 0 when
        // no searchables matches the query
        Set<MockSearchable> set2 = new HashSet<>();
        MockSearchable s4 = new MockSearchable();
        MockSearchable s5 = new MockSearchable();
        MockSearchable s6 = new MockSearchable();
        set2.add(s4);
        set2.add(s5);
        set2.add(s6);

        Set<MockSearchable> result2 = setSearcher.search(set2, "123");
        assertEquals(result2.size(), 0);
        assertTrue(s4.matchesCalled);
        assertTrue(s5.matchesCalled);
        assertTrue(s6.matchesCalled);
    }
}