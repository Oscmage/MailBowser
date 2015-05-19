package edu.chl.mailbowser.tag.handlers;

import edu.chl.mailbowser.email.models.IEmail;
import edu.chl.mailbowser.tag.models.ITag;

import java.util.Set;

/**
 * Created by OscarEvertsson on 19/05/15.
 */
public interface ITagHandler {

    void addTag(IEmail email, ITag tag);
    Set<IEmail> getEmails(ITag tag);
    Set<ITag> getTags(IEmail email);
    Set<ITag> getTags();
    void removeTag(IEmail email,ITag tag);
    void removeTag(ITag tag);
    boolean readTags(String filename);
    boolean writeTags(String filename);
}
