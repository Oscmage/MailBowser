package edu.chl.mailbowser.tag.models;

/**
 * Created by OscarEvertsson on 24/04/15.
 * The class Tag represents a certain email tag, examples could be "Work", "School" etc.
 */
public class Tag implements ITag {
    private String name;

    /**
     * Creates a Tag with the specified name.
     * @param name Creates a tag with the specified name.
     */

    public Tag(String name) {
        this.name = name.toLowerCase();
    }

    /**
     * Changes the name of the tag to the specified string.
     * @param name
     */
    @Override
    public void setTagName(String name) {
        this.name = name;
    }

    /**
     * Returns a string for the Tag's name.
     * @return
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Returns true if the object is a Tag and has the same name(String).
     * @param o
     * @return
     */
    @Override
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

    /**
     * Returns the hashCode value for this tag.
     * @return
     */
    @Override
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
