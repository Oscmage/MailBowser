package edu.chl.MailBowser.models;

/**
 * Created by OscarEvertsson on 09/04/15.
 *
 * An interface for Address.
 */
public interface IAddress {
    String toString();
    javax.mail.Address getJavaxAddress();
}
