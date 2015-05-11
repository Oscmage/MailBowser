package edu.chl.mailbowser.email.models;

import edu.chl.mailbowser.search.Searchable;

import javax.mail.Session;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by OscarEvertsson on 10/04/15.
 */
public interface IEmail extends Searchable, Serializable {
    javax.mail.Message getJavaxMessage(Session session);
    boolean isSent();
    IAddress getSender();
    List<IAddress> getReceivers();
    String getContent();
    String getSubject();
    Date getSentDate();
    Date getLastEditedDate();
    Date getCreatedDate();
    void setLastEditedDate();
    void setContent(String content);
    void setCreatedDate(Date createdDate);
    void setLastEditedDate(Date lastEditedDate);
    void setReceivers(List<IAddress> receivers);
    void setSender(IAddress sender);
    void setSubject(String subject);
    void setSent();
}
