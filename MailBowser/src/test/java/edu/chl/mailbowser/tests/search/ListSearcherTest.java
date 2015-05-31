package edu.chl.mailbowser.tests.search;

import edu.chl.mailbowser.tests.mock.MockSearchable;
import edu.chl.mailbowser.utils.search.ListSearcher;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by mats on 05/05/15.
 */
public class ListSearcherTest {

    private ListSearcher<MockSearchable> ListSearcher = new ListSearcher<>();

    @Before
    public void ListUp() throws Exception {
        ListSearcher = new ListSearcher<>();
    }

    @Test
    public void testSearch() throws Exception {
        // test if the search method gets called in all searchables, and that the size of the result List is the same
        // as the search List when all searchables matches the query
        List<MockSearchable> list1 = new ArrayList<>();
        MockSearchable s1 = new MockSearchable();
        MockSearchable s2 = new MockSearchable();
        MockSearchable s3 = new MockSearchable();
        list1.add(s1);
        list1.add(s2);
        list1.add(s3);

        List<MockSearchable> result1 = ListSearcher.search(list1, "");
        assertEquals(result1.size(), 3);
        assertTrue(s1.matchesCalled);
        assertTrue(s2.matchesCalled);
        assertTrue(s3.matchesCalled);

        // test if the search method gets called in all searchables, and that the size of the result List is 0 when
        // no searchables matches the query
        List<MockSearchable> list2 = new ArrayList<>();
        MockSearchable s4 = new MockSearchable();
        MockSearchable s5 = new MockSearchable();
        MockSearchable s6 = new MockSearchable();
        list2.add(s4);
        list2.add(s5);
        list2.add(s6);

        List<MockSearchable> result2 = ListSearcher.search(list2, "123");
        assertEquals(result2.size(), 0);
        assertTrue(s4.matchesCalled);
        assertTrue(s5.matchesCalled);
        assertTrue(s6.matchesCalled);
    }
}