package edu.chl.mailbowser.tag;

import edu.chl.mailbowser.email.IEmail;
import edu.chl.mailbowser.event.Event;
import edu.chl.mailbowser.event.EventBus;
import edu.chl.mailbowser.event.EventType;
import edu.chl.mailbowser.utils.Pair;
import edu.chl.mailbowser.utils.io.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by OscarEvertsson on 29/04/15.
 * TagHandler handles adding and removing tags from emails and vice versa.
 * Instance of this class exists in MainHandler.
 * Any class which needs to modify the tag/email relationship uses this class.
 */
public class TagHandler implements ITagHandler{

    //IMPORTANT! Name is based on From key to value
    private HashMap<ITag,Set<IEmail>> mapFromTagsToEmails = new HashMap<>();
    private HashMap<IEmail,Set<ITag>> mapFromEmailsToTags = new HashMap<>();


    /**
     * {@inheritDoc}
     *
     * @throws IllegalArgumentException when email or tag is null.
     */
    @Override
    public synchronized void addTagToEmail(IEmail email, ITag tag) {
        if (email == null || tag == null) {
            throw new IllegalArgumentException("addTagToEmail: Null on email or tag is not supported.");
        }

        // if tag doesn't have any emails since before, add it to the tag map
        if (!mapFromTagsToEmails.containsKey(tag)) {
            mapFromTagsToEmails.put(tag, new HashSet<>());
            EventBus.INSTANCE.publish(new Event(EventType.NEW_TAG_ADDED, tag));
        }
        mapFromTagsToEmails.get(tag).add(email);

        // if email doesn't have any tags since before, add it to the email map
        if (!mapFromEmailsToTags.containsKey(email)) {
            mapFromEmailsToTags.put(email, new HashSet<>());
        }
        mapFromEmailsToTags.get(email).add(tag);

        EventBus.INSTANCE.publish(new Event(EventType.ADDED_TAG_TO_EMAIL, new Pair<>(email, tag)));
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Set<IEmail> getEmailsWithTag(ITag tag){
        if(mapFromTagsToEmails.get(tag) != null) {
            return new HashSet<>(mapFromTagsToEmails.get(tag));
        }
        return new HashSet<>();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Set<ITag> getTagsWithEmail(IEmail email){
        if(mapFromEmailsToTags.get(email) != null) {
            return new HashSet<>(mapFromEmailsToTags.get(email));
        }
        return new HashSet<>();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Set<ITag> getTags(){
        return mapFromTagsToEmails.keySet();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void removeTagFromEmail(IEmail email,ITag tag){
        // check if the email exists in the email map
        if (mapFromEmailsToTags.containsKey(email)) {
            Set<ITag> tagSet = mapFromEmailsToTags.get(email);

            // check if the email has the tag
            if (tagSet != null && tagSet.contains(tag)) {
                // remove tag from set
                tagSet.remove(tag);

                // if no more tags on email, remove email from map
                if (tagSet.isEmpty()) {
                    mapFromEmailsToTags.remove(email);
                }

                // send event
                EventBus.INSTANCE.publish(new Event(EventType.REMOVED_TAG_FROM_EMAIL, new Pair<>(email, tag)));
            }
        }

        // check if the tag exists in the tag map
        if (mapFromTagsToEmails.containsKey(tag)) {
            Set<IEmail> emailSet = mapFromTagsToEmails.get(tag);

            // check if the tag has the email
            if (emailSet != null && emailSet.contains(email)) {
                // remove email from set
                emailSet.remove(email);

                // if no more emails for tag, remove tag from tag map
                if (emailSet.isEmpty()) {
                    mapFromTagsToEmails.remove(tag);

                    // send event
                    EventBus.INSTANCE.publish(new Event(EventType.REMOVED_TAG_COMPLETELY, tag));
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void eraseTag(ITag tag) {
        Set<IEmail> emailSet = mapFromTagsToEmails.remove(tag); //Gets the Set of emails belong to the tag.
        if(emailSet != null){
            for (IEmail email : emailSet) { //Loop through each email in the set
                Set <ITag> tagSet = mapFromEmailsToTags.get(email); //Get the tagSet for the email
                tagSet.remove(tag); //Remove the tag from the set.

                if (tagSet.isEmpty()) { //If the email only had this tag
                    mapFromEmailsToTags.remove(email);
                }
            }
        }
        EventBus.INSTANCE.publish(new Event(EventType.REMOVED_TAG_COMPLETELY, tag));
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public boolean readTags(String filename){
        IObjectReader<HashMap<ITag,Set<IEmail>>> objectReader = new ObjectReader<>();

        try{
            mapFromTagsToEmails = objectReader.read(filename); //Try to read from disk
        }catch (ObjectReadException e){
            return false;
        }
        Set<ITag> tempTags = mapFromTagsToEmails.keySet();

        // Rebuilds the mapFromEmailsToTags NOT vice versa!
        for (ITag tag: tempTags){ //Loop through all tags
            Set<IEmail> emails = mapFromTagsToEmails.get(tag); // Get every Set of emails for each tag
            for (IEmail email: emails){ //Loop through each email
                addTagToEmail(email, tag); //Add the tag to each email.
            }
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean writeTags(String filename){
        IObjectWriter<HashMap<ITag,Set<IEmail>>> objectReaderWriter = new ObjectWriter<>();
        return objectReaderWriter.write(mapFromTagsToEmails, filename);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void reset() {
        mapFromEmailsToTags.clear();
        mapFromTagsToEmails.clear();
        EventBus.INSTANCE.publish(new Event(EventType.TAGS_CLEARED, null));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TagHandler)) return false;

        TagHandler that = (TagHandler) o;

        if (mapFromEmailsToTags != null ? !mapFromEmailsToTags.equals(that.mapFromEmailsToTags) : that.mapFromEmailsToTags != null)
            return false;
        if (mapFromTagsToEmails != null ? !mapFromTagsToEmails.equals(that.mapFromTagsToEmails) : that.mapFromTagsToEmails != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = mapFromTagsToEmails != null ? mapFromTagsToEmails.hashCode() : 0;
        result = 31 * result + (mapFromEmailsToTags != null ? mapFromEmailsToTags.hashCode() : 0);
        return result;
    }
}
