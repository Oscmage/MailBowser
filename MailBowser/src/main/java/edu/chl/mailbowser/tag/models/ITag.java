package edu.chl.mailbowser.tag.models;

import edu.chl.mailbowser.search.Searchable;

/**
 * Created by OscarEvertsson on 24/04/15.
 */
public interface ITag extends Searchable {
   void setTagName(String name);
   String getName();
   boolean equals(Object o);
   int hashCode();
}
