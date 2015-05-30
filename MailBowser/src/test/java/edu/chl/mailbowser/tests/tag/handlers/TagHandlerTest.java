package edu.chl.mailbowser.tests.tag.handlers;

import edu.chl.mailbowser.email.IEmail;
import edu.chl.mailbowser.tag.ITag;
import edu.chl.mailbowser.tag.TagHandler;
import edu.chl.mailbowser.tests.mock.MockEmail;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
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
        assertTrue(tagHandler.getTagsWithEmail(email).isEmpty());
    }

    @Test
    public void testGetEmailsWithTagNull(){
        //Tests if the getter returns a completely empty HashSet when passing in null
        assertTrue(tagHandler.getEmailsWithTag(null).size() == 0);
    }

    @Test
    public void testGetTagsWithEmailNull(){
        //Tests if the getter returns a completely empty HashSet when passing in null
        assertTrue(tagHandler.getTagsWithEmail(null).size() == 0);
    }


    @Test
    public void testAddTag() {

        tagHandler.addTagToEmail(email, tagOne);

        //Tests if you get the same email after adding it with a certain tag
        assertEquals(tagHandler.getEmailsWithTag(tagOne).toArray()[0], email);

        //Tests if you get the same tag after adding it with a certain email.
        assertEquals(tagHandler.getTagsWithEmail(email).toArray()[0], tagOne);

        //Tests if addTag actually adds it to the key set (assumes normal getter works)
        assertTrue(tagHandler.getTags().contains(tagOne));
    }

    @Test
    public void testMultipleAddTag() {
        // Single email with multiple tags?
        tagHandler.addTagToEmail(email, tagTwo);
        //Add same email and tag to see if they gets added twice
        tagHandler.addTagToEmail(email,tagTwo);
        tagHandler.addTagToEmail(email, tagOne);

        //Contains first added tag?
        assertTrue(tagHandler.getTagsWithEmail(email).contains(tagOne));
        //Contains tag nr 2?
        assertTrue(tagHandler.getTagsWithEmail(email).contains(tagTwo));
        //Correct size after adding same tag and email twice?
        assertTrue(tagHandler.getTagsWithEmail(email).size() == 2);
    }


    @Test(expected = IllegalArgumentException.class)
    public void testAddTagToEmailException() {
        //Null test
        tagHandler.addTagToEmail(null,null);
        tagHandler.addTagToEmail(null, tagOne);
        tagHandler.addTagToEmail(email, null);
    }

    @Test
    public void testRemoveTag() {
        //Adds two x tags to an email
        tagHandler.addTagToEmail(email, tagOne);
        tagHandler.addTagToEmail(email, tagTwo);

        //removes them from the email
        tagHandler.removeTagFromEmail(email, tagOne);
        tagHandler.removeTagFromEmail(email, tagTwo);
        //Should now be empty since both removed
        assertTrue(tagHandler.getTagsWithEmail(email).isEmpty());


        //Reset tagHandler
        tagHandler = new TagHandler();
        //Adds two x tags from an email
        tagHandler.addTagToEmail(email, tagOne);
        tagHandler.addTagToEmail(email, tagTwo);

        //Removes ONE of the tags
        tagHandler.removeTagFromEmail(email, tagOne);

        //Checks if the email no longer is tagged with "tagOne" and if still tagged with "tagTwo"
        assertFalse(tagHandler.getTagsWithEmail(email).contains(tagOne));
        assertTrue(tagHandler.getTagsWithEmail(email).contains(tagTwo));
    }

    @Test
    public void testEraseTag(){
        IEmail secondMockEmail = new MockEmail();
        tagHandler.addTagToEmail(email,tagOne);
        tagHandler.addTagToEmail(email,tagTwo);
        tagHandler.addTagToEmail(secondMockEmail,tagOne);

        tagHandler.eraseTag(tagOne);

        //The tag shouldn't belong in the MapFromTagsToEmails
        assertFalse(tagHandler.getTags().contains(tagOne));

        //The tag shouldn't belong in the MapFromEmailsToTags
        assertFalse(tagHandler.getTagsWithEmail(email).contains(tagOne));
        assertFalse(tagHandler.getTagsWithEmail(secondMockEmail).contains(tagOne));

        //Check that tagTwo still exists on the email
        assertTrue(tagHandler.getTagsWithEmail(email).contains(tagTwo));

        //Checks if secondMockEmail has been removed
        assertTrue(tagHandler.getTagsWithEmail(secondMockEmail).isEmpty());

    }

    @Test
    public void testReset(){
        tagHandler.addTagToEmail(email, tagOne);
        tagHandler.addTagToEmail(email, tagTwo);
        tagHandler.reset();

        //Any trace of the tag in MapFromTagsToEmail ?
        assertTrue(tagHandler.getTags().isEmpty());

        //Any trace of the tag in MapFromEmailsToTags ?
        assertTrue(tagHandler.getEmailsWithTag(tagOne).isEmpty());
        assertTrue(tagHandler.getEmailsWithTag(tagTwo).isEmpty());
    }

    @Test
    public void testEquals(){
        //Same object
        assertTrue(tagHandler.equals(tagHandler));
        //tagHandler equals a tag?
        assertFalse(tagHandler.equals(tagOne));
        //Null test
        assertFalse(tagHandler.equals(null));

        //Creates other tagHandler and manipulates their maps.
        TagHandler otherTagHandler = new TagHandler();
        otherTagHandler.addTagToEmail(email, tagOne);

        //Since other tagHandler doesn't got the same objects in it's maps should return false.
        assertFalse(tagHandler.equals(otherTagHandler));

        //new tagHandler with same maps should return true
        assertTrue(tagHandler.equals(new TagHandler()));
    }

    @Test
    public void testHashCode(){
        TagHandler otherTagHandler = new TagHandler();
        //hashCode returns same value?
        assertTrue(tagHandler.hashCode() == tagHandler.hashCode());
        //Same maps should return same hashCode
        assertTrue(tagHandler.hashCode() == otherTagHandler.hashCode());

        //Manipulates the maps in otherTagHandler
        otherTagHandler.addTagToEmail(email, tagOne);

        //Since not same maps should return false
        assertFalse(tagHandler.hashCode() == otherTagHandler.hashCode());

        //Manipulate the tagHandler with same objects so the maps are identical
        tagHandler.addTagToEmail(email,tagOne);

        //Maps should now be identical and return true
        assertTrue(tagHandler.hashCode() == otherTagHandler.hashCode());
    }

}
