package edu.chl.mailbowser.tag;

import edu.chl.mailbowser.utils.search.Searchable;

import java.io.Serializable;

/**
 * Created by OscarEvertsson on 24/04/15.
 */
public interface ITag extends Searchable, Serializable, Comparable<ITag> {

   /**
    * Changes the name of the tag to the specified string.
    * @param name the new name for the tag
    */
   void setTagName(String name);

   /**
    * Returns a string for the Tag's name.
    * @return the current name of the tag.
    */
   String getName();

}
