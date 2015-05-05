package edu.chl.mailbowser.tag.models;

import edu.chl.mailbowser.search.Searchable;

/**
 * Created by OscarEvertsson on 24/04/15.
 * The class Tag represents a certain email tag, examples could be "Work", "School" etc.
 */
public class Tag implements ITag, Searchable {
    private String name;

    /**
     * Creates a Tag with the specified name.
     * @param name Creates a tag with the specified name.
     */
    public Tag(String name) {
        this.name = name.toLowerCase();
    }

    @Override
    public void setTagName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o == null) {
            return false;
        } else if (!o.getClass().equals(this.getClass())) {
            return false;
        }
        Tag t = (Tag)o;
        return name.equals(t.getName());
    }

    public int hashCode() {
        return name.hashCode()*11;
    }

    /**
     * Checks whether or not this tag matches a given string.
     *
     * @param query the string to match against
     * @return true if the tag name begins with str, false otherwise
     */
    @Override
    public boolean matches(String query) {
        return query != null && name.startsWith(query.toLowerCase());
    }
}
