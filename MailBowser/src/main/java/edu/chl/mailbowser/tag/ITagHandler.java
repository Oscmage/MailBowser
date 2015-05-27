package edu.chl.mailbowser.tag;

import edu.chl.mailbowser.email.IEmail;

import java.util.Set;

/**
 * Created by OscarEvertsson on 19/05/15.
 * This interface represents a handler for tags and emails.
 */
public interface ITagHandler {

    /**
     * Adds the tag to the email and vice versa.
     * Synchronized since we're reAdding everything on startup and
     * during that time you could possible add a tag at the same time.
     * @param email the email to add a tag on
     * @param tag the tag to add on the email
     */
    void addTagToEmail(IEmail email, ITag tag);

    /**
     * Returns a set of emails for the given tag.
     * @param tag the tag for which emails you want.
     * @return the emails with the tag
     */
    Set<IEmail> getEmailsWithTag(ITag tag);

    /**
     * Returns a set of tags for the given email.
     * @param email the email for which tags you want.
     * @return the tags for the specified email.
     */
    Set<ITag> getTagsWithEmail(IEmail email);

    /**
     * Returns all the tag(s).
     * @return all tag(s).
     */
    Set<ITag> getTags();


    /**
     * Removes the tag from the specified email.
     * By using this method the email will also be removed from the tags set of emails
     * @param email the email to remove from.
     * @param tag the tag you want to remove from the certain email.
     */
    void removeTagFromEmail(IEmail email,ITag tag);

    /**
     * Removes the specified tag completely.
     * @param tag the tag to remove.
     */
    void eraseTag(ITag tag);

    /**
     * Reads in the mapFromTagsToEmails HashMaps from disk
     * Then builds the mapFromEmailsToTags HashMap from mapFromTagsToEmails
     * @param filename location of the file
     * @return true if the reading of mapFromTagsToEmails was successful
     */
    boolean readTags(String filename);

    /**
     * Writes the mapFromTagsToEmails HashMap to disk
     * @param filename the location of the file
     * @return return true on success
     */
    boolean writeTags(String filename);

    /**
     * Resets the tag handler. This method clears all current relationships between tags and emails.
     */
    void reset();
}
