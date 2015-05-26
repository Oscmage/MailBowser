package edu.chl.mailbowser.tag;

import edu.chl.mailbowser.email.IEmail;
import edu.chl.mailbowser.event.Event;
import edu.chl.mailbowser.event.EventBus;
import edu.chl.mailbowser.event.EventType;
import edu.chl.mailbowser.io.*;
import edu.chl.mailbowser.utils.Pair;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by OscarEvertsson on 29/04/15.
 * TagHandler handles adding and removing tags from emails and vice versa.
 */
public class TagHandler implements ITagHandler{

    //IMPORTANT! Name is based on From key to value
    private Map<ITag,Set<IEmail>> mapFromTagsToEmails = new HashMap<>();
    private Map<IEmail,Set<ITag>> mapFromEmailsToTags = new HashMap<>();


    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void addTagToEmail(IEmail email, ITag tag){
        if (!mapFromTagsToEmails.containsKey(tag)) { //If key doesn't exists
            mapFromTagsToEmails.put(tag, new HashSet<>()); //Create key with empty set
        }
        mapFromTagsToEmails.get(tag).add(email); // Add the value to the set

        if (!mapFromEmailsToTags.containsKey(email)) { //If key doesn't exists
            mapFromEmailsToTags.put(email, new HashSet<>()); //Create key with empty set
        }
        mapFromEmailsToTags.get(email).add(tag); // Add the value to the set

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
        if (mapFromEmailsToTags.containsKey(email)) { //If key exists
            Set<ITag> tagSet = mapFromEmailsToTags.get(email); //Get set for the key
            tagSet.remove(tag); // Remove the tag from the set
            if (tagSet.isEmpty()) { //If set is empty
                mapFromEmailsToTags.remove(email); //Remove the key
            }
        }

        if (mapFromTagsToEmails.containsKey(tag)) { // If key exists
            Set<IEmail> emailSet = mapFromTagsToEmails.get(tag); // Get set for the key
            emailSet.remove(email); // Remove the email from the set
            if (emailSet.isEmpty()) { //If set is empty
                mapFromTagsToEmails.remove(tag); // Remove the key
            }
        }

        EventBus.INSTANCE.publish(new Event(EventType.REMOVED_TAG_FROM_EMAIL, new Pair<>(email, tag)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void eraseTag(ITag tag) {
        Set<IEmail> emailSet = mapFromTagsToEmails.remove(tag); //Gets the Set of emails belong to the tag.

        for (IEmail email : emailSet) { //Loop through each email in the set
            Set <ITag> tagSet = mapFromEmailsToTags.get(email); //Get the tagSet for the email
            tagSet.remove(tag); //Remove the tag from the set.

            if (tagSet.isEmpty()) { //If the email only had this tag
                mapFromEmailsToTags.remove(email);
            }
        }
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public boolean readTags(String filename){
        IObjectReader<HashMap> objectReader = new ObjectReader<>();

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
        IObjectWriter<HashMap> objectReaderWriter = new ObjectWriter<>();
        return objectReaderWriter.write((HashMap) mapFromTagsToEmails, filename);
    }
}
