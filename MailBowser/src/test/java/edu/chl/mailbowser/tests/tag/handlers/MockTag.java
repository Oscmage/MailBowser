package edu.chl.mailbowser.tests.tag.handlers;

import edu.chl.mailbowser.tag.ITag;

/**
 * Created by filip on 19/05/15.
 * Mock class for Tag
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

    @Override
    public int compareTo(ITag o) {
        return 0;
    }
}
