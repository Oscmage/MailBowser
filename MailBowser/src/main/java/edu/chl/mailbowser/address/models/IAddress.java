package edu.chl.mailbowser.address.models;

import edu.chl.mailbowser.search.Searchable;

/**
 * Created by OscarEvertsson on 09/04/15.
 *
 * An interface for Address.
 */
public interface IAddress extends Searchable {
    String toString();
    javax.mail.Address getJavaxAddress();
    String getString();
    boolean equals(Object o);
    int hashCode();
}
