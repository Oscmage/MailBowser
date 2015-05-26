package edu.chl.mailbowser.tag;

/**
 * Created by OscarEvertsson on 24/04/15.
 * The class Tag represents a certain email tag, examples could be "Work", "School" etc.
 */
public class Tag implements ITag {
    private String name;

    /**
     * Creates a Tag with the specified name.
     */
    public Tag(String name) {
        this.name = name.toLowerCase();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void setTagName(String name) {
        this.name = name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Returns true if the object is a tag + has the same name (String).
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
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return name.hashCode()*11;
    }

    /**
     * Checks whether or not this tag matches the given string.
     */
    @Override
    public boolean matches(String query) {
        return query != null && name.startsWith(query.toLowerCase());
    }
}
