package edu.chl.mailbowser.tag.handlers;

import edu.chl.mailbowser.email.models.IEmail;
import edu.chl.mailbowser.event.Event;
import edu.chl.mailbowser.event.EventBus;
import edu.chl.mailbowser.event.EventType;
import edu.chl.mailbowser.io.*;
import edu.chl.mailbowser.tag.models.ITag;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by OscarEvertsson on 29/04/15.
 */
public class TagHandler{
    private static TagHandler instance = new TagHandler();

    public static TagHandler getInstance(){
        return instance;
    }

    private TagHandler(){}

    private Map<ITag,Set<IEmail>> tags = new HashMap<>();
    private Map<IEmail,Set<ITag>> emails = new HashMap<>();

    /**
     * Adds tag to an email.
     * @param email
     * @param tag
     */
    public void addTag(IEmail email, ITag tag){
        if (!tags.containsKey(tag)) {
            tags.put(tag, new HashSet<>());
        }
        tags.get(tag).add(email);

        if (!emails.containsKey(email)) {
            emails.put(email, new HashSet<>());
        }
        emails.get(email).add(tag);

        EventBus.INSTANCE.publish(new Event(EventType.ADD_TAG, tag));
    }

    /**
     * Returns a set of emails for the given tag.
     * @param tag
     * @return
     */
    public Set<IEmail> getEmails(ITag tag){
        return new HashSet<>(tags.get(tag));
    }

    /**
     * Returns a set of tags for the given email.
     * @param email
     * @return
     */
    public Set<ITag> getTags(IEmail email){
        return new HashSet<>(emails.get(email));
    }

    /**
     * returns the tag(s).
     * @return
     */
    public Set<ITag> getTags(){
        return tags.keySet();
    }

    /**
     * Removes a tag from the specified email.
     * @param email
     * @param tag
     */
    public void removeTag(IEmail email,ITag tag){
        Set<ITag> tagSet = emails.get(email);
        tagSet.remove(tag);
        if (tagSet.isEmpty()) {
            emails.remove(email);
        }

        Set<IEmail> emailSet = tags.get(tag);
        emailSet.remove(email);
        if (emailSet.isEmpty()) {
            tags.remove(tag);
        }
        EventBus.INSTANCE.publish(new Event(EventType.REMOVE_TAG,tag));
    }

    /**
     * Removes the specified tag completly
     * @param tag
     */
    public void removeTag(ITag tag) {
        Set<IEmail> emailSet = tags.remove(tag);

        for (IEmail email : emailSet) { 
            Set <ITag> tagSet = emails.get(email);
            tagSet.remove(tag);

            if (tagSet.isEmpty()) {
                emails.remove(email);
            }
        }
    }

    /**
     * Reads in the tags HashMap from disk
     * Then builds the emails HashMap from tags
     * @param filename location of the file
     * @return true if the reading of tags was successful
     */
    public boolean readTags(String filename){
        IObjectReader<HashMap> objectReader = new ObjectReader<>();

        try{
            tags = objectReader.read(filename);
        }catch (ObjectReadException e){
            return false;
        }
        Set<ITag> tempTags = tags.keySet();

        for (ITag tag: tempTags){
            Set<IEmail> emails = tags.get(tag);
            for (IEmail email: emails){
                addTag(email,tag);
            }
        }
        return true;
    }

    /**
     * Writes the tags HashMap to disk
     * @param filename the location of the file
     * @return return true on success
     */
    public boolean writeTags(String filename){
        IObjectWriter<HashMap> objectReaderWriter = new ObjectWriter<>();
        return objectReaderWriter.write((HashMap)tags, filename);
    }
}
