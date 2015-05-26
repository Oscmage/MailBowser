package edu.chl.mailbowser.tag;

import edu.chl.mailbowser.search.Searchable;

import java.io.Serializable;

/**
 * Created by OscarEvertsson on 24/04/15.
 */
public interface ITag extends Searchable, Serializable {
   void setTagName(String name);
   String getName();
   boolean equals(Object o);
   int hashCode();
}
