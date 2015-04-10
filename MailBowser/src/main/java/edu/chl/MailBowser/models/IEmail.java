package edu.chl.MailBowser.models;

/**
 * Created by OscarEvertsson on 10/04/15.
 */
public interface IEmail {
    javax.mail.Message getJavaxMessage();
}
