package edu.chl.MailBowser.models;

/**
 * Created by OscarEvertsson on 09/04/15.
 */

/**
 * A interface for Address.
 * The interface also assumes you want set/get and a isValid method for your address.
 */
public interface IAddress {
    String toString();
    javax.mail.Address getJavaxAddress();
}
