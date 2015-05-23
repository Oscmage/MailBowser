package edu.chl.mailbowser.tests.tag.handlers;

import edu.chl.mailbowser.tag.models.ITag;

/**
 * Created by filip on 19/05/15.
 */
public class MockTag implements ITag {

    @Override
    public void setTagName(String name) {

    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public boolean matches(String query) {
        return false;
    }
}
