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


    /**
     * Returns the instance of the singleton.
     * @return
     */
    public static TagHandler getInstance(){
        return instance;
    }

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





}
