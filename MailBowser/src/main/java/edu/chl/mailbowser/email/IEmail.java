package edu.chl.mailbowser.email;

import edu.chl.mailbowser.search.Searchable;

import javax.mail.Session;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by OscarEvertsson on 10/04/15.
 */
public interface IEmail extends Searchable, Serializable, Comparable<IEmail> {
    javax.mail.Message getJavaMailMessage(Session session);
    IAddress getSender();
    List<IAddress> getTo();
    List<IAddress> getCc();
    List<IAddress> getBcc();
    List<IAddress> getAllRecipients();
    String getContent();
    String getSubject();
    Date getSentDate();
    Date getReceivedDate();
    void setContent(String content);
    void setSender(IAddress sender);
    void setSubject(String subject);
}
