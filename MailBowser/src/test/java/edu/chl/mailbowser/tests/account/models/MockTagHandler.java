package edu.chl.mailbowser.tests.account.models;

import edu.chl.mailbowser.email.IEmail;
import edu.chl.mailbowser.tag.ITagHandler;
import edu.chl.mailbowser.tag.ITag;

import java.util.Set;

/**
 * Created by mats on 26/05/15.
 */
public class MockTagHandler implements ITagHandler {
    @Override
    public void addTagToEmail(IEmail email, ITag tag) {

    }

    @Override
    public Set<IEmail> getEmailsWithTag(ITag tag) {
        return null;
    }

    @Override
    public Set<ITag> getTagsWithEmail(IEmail email) {
        return null;
    }

    @Override
    public Set<ITag> getTags() {
        return null;
    }

    @Override
    public void removeTagFromEmail(IEmail email, ITag tag) {

    }

    @Override
    public void eraseTag(ITag tag) {

    }

    @Override
    public boolean readTags(String filename) {
        return false;
    }

    @Override
    public boolean writeTags(String filename) {
        return false;
    }
}
