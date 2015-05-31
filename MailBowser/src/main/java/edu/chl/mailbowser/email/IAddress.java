package edu.chl.mailbowser.email;

import edu.chl.mailbowser.utils.search.Searchable;

import java.io.Serializable;

/**
 * Created by OscarEvertsson on 09/04/15.
 *
 * An interface for Address.
 */
public interface IAddress extends Searchable, Serializable, Comparable<IAddress> {

    /**
     * Returns a string representation of the address, in the format user@domain.com
     *
     * @return a string representation of the address
     */
    String toString();

    /**
     * @return a copy of the javax.mail.InternetAddress object which holds the information
     */
    javax.mail.Address getJavaMailAddress();

    /**
     * Returns a string representation of the address, in the format user@domain.com
     * @return
     */
    String getString();
    boolean equals(Object o);
    int hashCode();
}
