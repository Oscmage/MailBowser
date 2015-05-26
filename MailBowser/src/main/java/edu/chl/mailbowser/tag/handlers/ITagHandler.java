package edu.chl.mailbowser.tag.handlers;

import edu.chl.mailbowser.email.models.IEmail;
import edu.chl.mailbowser.tag.models.ITag;

import java.io.Serializable;
import java.util.Set;

/**
 * Created by OscarEvertsson on 19/05/15.
 */
public interface ITagHandler {
    void addTagToEmail(IEmail email, ITag tag);
    Set<IEmail> getEmailsWith(ITag tag);
    Set<ITag> getTagsWith(IEmail email);
    Set<ITag> getTags();
    void removeTagFromEmail(IEmail email,ITag tag);
    void eraseTag(ITag tag);
    boolean readTags(String filename);
    boolean writeTags(String filename);
}
