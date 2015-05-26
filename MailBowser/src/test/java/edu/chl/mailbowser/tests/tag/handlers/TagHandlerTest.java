package edu.chl.mailbowser.tests.tag.handlers;

import edu.chl.mailbowser.email.IEmail;
import edu.chl.mailbowser.tag.TagHandler;
import edu.chl.mailbowser.tag.ITag;
import edu.chl.mailbowser.tests.account.models.MockEmail;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


/**
 * Created by filip on 19/05/15.
 * A test class for TagHandler
 */
public class TagHandlerTest {

    private TagHandler tagHandler;
    private IEmail email;
    private ITag tagOne;
    private ITag tagTwo;

    @Before
    public void init() {
        tagHandler = new TagHandler();
        email = new MockEmail();
        tagOne = new MockTag();
        tagTwo = new MockTag();
    }

    @Test
    public void testEmptyTagsMap() {
        // Is it empty?
        assertTrue(tagHandler.getTagsWithEmail(new MockEmail()).isEmpty());
    }


    @Test
    public void testAddTag() {

        tagHandler.addTagToEmail(email, tagOne);

        //Tests if you get the same email after adding it with a certain tag
        assertEquals(tagHandler.getEmailsWithTag(tagOne).toArray()[0], email);

        //Tests if you get the same tag after adding it with a certain email.
        assertEquals(tagHandler.getTagsWithEmail(email).toArray()[0], tagOne);
    }

    @Test
    public void testMultipleAddTag() {
        // Single email with multiple tags?
        tagHandler.addTagToEmail(email, tagTwo);
        tagHandler.addTagToEmail(email, tagOne);

        //Contains first added tag?
        assertTrue(tagHandler.getTagsWithEmail(email).contains(tagOne));
        //Contains tag nr 2?
        assertTrue(tagHandler.getTagsWithEmail(email).contains(tagTwo));
    }


    @Test(expected = IllegalArgumentException.class)
    public void testAddTagToEmailException() {
        //Null test
        tagHandler.addTagToEmail(null,null);
    }

    @Test
    public void testRemoveTag() {

        tagHandler.addTagToEmail(email, tagOne);
        tagHandler.eraseTag(tagOne);
        assertTrue(tagHandler.getTagsWithEmail(email).isEmpty());
    }

}
