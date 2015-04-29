package edu.chl.MailBowser;

import edu.chl.MailBowser.models.IEmail;
import edu.chl.MailBowser.models.ITag;

import java.util.*;

/**
 * Created by OscarEvertsson on 29/04/15.
 */
public class TagHandler{
    private static TagHandler instance = new TagHandler();
    private Map<ITag,Set<IEmail>> tags = new HashMap<>();
    private Map<IEmail,Set<ITag>> emails = new HashMap<>();

    private TagHandler(){}



    public static TagHandler getInstance(){
        return instance;
    }

    public void addTag(IEmail email, ITag tag){
        addTagToEmail(email,tag);
        addEmailToTag(email,tag);
    }

    private void addTagToEmail(IEmail email,ITag tag){
        Set<ITag> tagSet;
        if(this.emails.containsKey(email)){
            tagSet = this.emails.get(email);
        } else {
            tagSet = new HashSet<>();
            this.emails.put(email,tagSet);
        }
        tagSet.add(tag);
    }

    private void addEmailToTag(IEmail email,ITag tag){
        Set<IEmail> emailSet;
        if(this.tags.containsKey(tag)){
            emailSet = this.tags.get(tag);
        } else {
            emailSet = new HashSet<>();
            this.tags.put(tag, emailSet);
        }
        emailSet.add(email);
    }

    /**
     * Returns the email(s) for the specified tag.
     * @param tag
     * @return
     */
    public Set<IEmail> getEmails(ITag tag){
        return tags.get(tag);
    }

    /**
     * Returns the tag(s) for a certain email.
     * @param email
     * @return
     */
    public Set<ITag> getTags(IEmail email){
        return emails.get(email);
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
        this.emails.get(email).remove(tag);
        this.tags.get(tag).remove(email);
    }

    /**
     * Removes the specified tag completly
     * @param tag
     */
    public void removeTag(ITag tag) {
        removeTagFromEmail(tag);
        removeEmailFromTag(tag);
    }

    private void removeTagFromEmail(ITag tag){
        Object[] emails = this.tags.get(tag).toArray(); //Gets all email with the specified tag
        for (int i = 0; i < emails.length; i++) { //Loops through every email with the tag and removes it (Removes from email Map).
            this.emails.get(emails[i]).remove(tag);
        }
    }

    private void removeEmailFromTag(ITag tag){
        Set<IEmail> setOfEmails = this.tags.get(tag);
        setOfEmails.clear();
        this.tags.remove(tag);
    }




}
