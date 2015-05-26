package edu.chl.mailbowser.tests.tag.handlers;

import edu.chl.mailbowser.email.models.IEmail;
import edu.chl.mailbowser.tag.handlers.TagHandler;
import edu.chl.mailbowser.tag.models.ITag;
import edu.chl.mailbowser.tests.account.models.MockEmail;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


/**
 * Created by filip on 19/05/15.
 */
public class TagHandlerTest {

    private TagHandler tagHandler;

    @Before
    public void init() {
        tagHandler = new TagHandler();
    }

    @Test
    public void testEmptyTagsMap() {
        // Is it empty?
        assertTrue(tagHandler.getTagsWith(new MockEmail()).isEmpty());
    }

    @Test
    public void testAddTag() {
        IEmail e1 = new MockEmail();
        ITag t1 = new MockTag();

        tagHandler.addTagToEmail(e1, t1);

        assertEquals(tagHandler.getEmailsWith(t1).toArray()[0], e1);
        assertEquals(tagHandler.getTagsWith(e1).toArray()[0], t1);
    }

    @Test
    public void testMultipleAddTag() {
        IEmail e1 = new MockEmail();
        ITag t1 = new MockTag();
        ITag t2 = new MockTag();

        // Single email with multiple tags?
        tagHandler.addTagToEmail(e1, t2);
        tagHandler.addTagToEmail(e1, t1);
        assertTrue(tagHandler.getTagsWith(e1).contains(t1));
        assertTrue(tagHandler.getTagsWith(e1).contains(t2));
    }

    @Test
    public void testRemoveTag() {
        IEmail e1 = new MockEmail();
        ITag t1 = new MockTag();

        tagHandler.addTagToEmail(e1, t1);
        tagHandler.eraseTag(t1);
        assertTrue(tagHandler.getTagsWith(e1).isEmpty());
    }

}
