package edu.chl.MailBowser.models;

import javax.mail.Session;

/**
 * Created by OscarEvertsson on 10/04/15.
 */
public interface IEmail {
    javax.mail.Message getJavaxMessage(Session session);
}
