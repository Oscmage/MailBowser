package edu.chl.MailBowser.models;

/**
 * Created by OscarEvertsson on 09/04/15.
 */

/**
 * A class implements the Address interface if you want to create any form of address.
 * The interface also assumes you want set/get and a isValid method for your address.
 */
public interface Address {
    void setAddress(String s);
    String toString();
    int isValid();
}
