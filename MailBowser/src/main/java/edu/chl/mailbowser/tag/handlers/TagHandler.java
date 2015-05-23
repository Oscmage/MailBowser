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
 * TagHandler handles adding and removing tags from emails and vice versa.
 */
public class TagHandler implements ITagHandler{

    private Map<ITag,Set<IEmail>> mapFromTagsToEmails = new HashMap<>();
    private Map<IEmail,Set<ITag>> mapFromEmailsToTags = new HashMap<>();

    /**
     * Adds the tag to the email and vice versa.
     * Synchronized since we're reAdding everything on startup and
     * during that time you could possible add a tag at the same time.
     * @param email
     * @param tag
     */
    @Override
    public synchronized void addTag(IEmail email, ITag tag){
        if (!mapFromTagsToEmails.containsKey(tag)) {
            mapFromTagsToEmails.put(tag, new HashSet<>());
        }
        mapFromTagsToEmails.get(tag).add(email);

        if (!mapFromEmailsToTags.containsKey(email)) {
            mapFromEmailsToTags.put(email, new HashSet<>());
        }
        mapFromEmailsToTags.get(email).add(tag);

        EventBus.INSTANCE.publish(new Event(EventType.ADD_TAG, tag));
    }

    /**
     * Returns a set of emails for the given tag.
     * @param tag
     * @return
     */
    @Override
    public Set<IEmail> getEmailsWith(ITag tag){
        return new HashSet<>(mapFromTagsToEmails.get(tag));
    }

    /**
     * Returns a set of tags for the given email.
     * @param email
     * @return
     */
    @Override
    public Set<ITag> getTagsWith(IEmail email){
        if(mapFromEmailsToTags.get(email) != null) {
            return new HashSet<>(mapFromEmailsToTags.get(email));
        }
        return new HashSet<>();
    }

    /**
     * Returns all the tag(s).
     * @return
     */
    @Override
    public Set<ITag> getTags(){
        return mapFromTagsToEmails.keySet();
    }

    /**
     * Removes the tag from the specified email(from both maps.).
     * @param email
     * @param tag
     */
    @Override
    public synchronized void removeTagFromEmail(IEmail email,ITag tag){
        if (mapFromEmailsToTags.containsKey(email)) {
            Set<ITag> tagSet = mapFromEmailsToTags.get(email);
            tagSet.remove(tag);
            if (tagSet.isEmpty()) {
                mapFromEmailsToTags.remove(email);
            }
        }

        if (mapFromTagsToEmails.containsKey(tag)) {
            Set<IEmail> emailSet = mapFromTagsToEmails.get(tag);
            emailSet.remove(email);
            if (emailSet.isEmpty()) {
                mapFromTagsToEmails.remove(tag);
            }
        }

        EventBus.INSTANCE.publish(new Event(EventType.REMOVE_TAG,tag));
    }

    /**
     * Removes the specified tag from all mapFromEmailsToTags and mapFromTagsToEmails.
     * @param tag
     */
    @Override
    public synchronized void eraseTag(ITag tag) {
        Set<IEmail> emailSet = mapFromTagsToEmails.remove(tag);

        for (IEmail email : emailSet) { 
            Set <ITag> tagSet = mapFromEmailsToTags.get(email);
            tagSet.remove(tag);

            if (tagSet.isEmpty()) {
                mapFromEmailsToTags.remove(email);
            }
        }
    }

    /**
     * Reads in the mapFromTagsToEmails HashMaps from disk
     * Then builds the mapFromEmailsToTags HashMap from mapFromTagsToEmails
     * @param filename location of the file
     * @return true if the reading of mapFromTagsToEmails was successful
     */
    @Override
    public boolean readTags(String filename){
        IObjectReader<HashMap> objectReader = new ObjectReader<>();

        try{
            mapFromTagsToEmails = objectReader.read(filename);
        }catch (ObjectReadException e){
            return false;
        }
        Set<ITag> tempTags = mapFromTagsToEmails.keySet();

        for (ITag tag: tempTags){
            Set<IEmail> emails = mapFromTagsToEmails.get(tag);
            for (IEmail email: emails){
                addTag(email,tag);
            }
        }
        return true;
    }

    /**
     * Writes the mapFromTagsToEmails HashMap to disk
     * @param filename the location of the file
     * @return return true on success
     */
    @Override
    public boolean writeTags(String filename){
        IObjectWriter<HashMap> objectReaderWriter = new ObjectWriter<>();
        return objectReaderWriter.write((HashMap) mapFromTagsToEmails, filename);
    }
}
