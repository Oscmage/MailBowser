package edu.chl.MailBowser.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by OscarEvertsson on 24/04/15.
 * The class Tag represents a certain email tag, examples could be "Work", "School" etc.
 */
public class Tag implements ITag{
    private String name;
    private List<IEmail> emails;

    /**
     * Creates a Tag with the specified name.
     * @param name Creates a tag with the specified name.
     */
    public Tag(String name){
        this.name = name;
        this.emails = new ArrayList<>();
    }

    /**
     * Sets the tag's name to the given String.
     * @param name
     */
    public void setTagName(String name){
        this.name = name;
    }

    /**
     * Adds the list of emails to the current list for the tag.
     * @param emails
     */
    public void addEmails(List<IEmail> emails){
        this.emails.addAll(emails);
    }

    /**
     * Gives the current list of all emails with this tag.
     * @return
     */
    public List<IEmail> getEmails(){
        return emails;
    }

    /**
     * Add the given email to the tag.
     * @param email
     */
    public void addEmail(IEmail email){
        this.emails.add(email);
    }

    /**
     * Removes the specified email from the tag.
     * @param email
     * @return true if successfully removed or false if not.
     */
    public boolean removeEmail(IEmail email){
       return this.emails.remove(email);
    }



}
