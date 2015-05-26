package edu.chl.mailbowser.email;

import edu.chl.mailbowser.search.Searchable;

import java.io.Serializable;

/**
 * Created by OscarEvertsson on 09/04/15.
 *
 * An interface for Address.
 */
public interface IAddress extends Searchable, Serializable{
    String toString();
    javax.mail.Address getJavaMailAddress();
    String getString();
    boolean equals(Object o);
    int hashCode();
}
