package edu.chl.MailBowser.models;

import javax.mail.Session;
import java.util.Date;
import java.util.List;

/**
 * Created by OscarEvertsson on 10/04/15.
 */
public interface IEmail {
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
    List<ITag> getTags();
    void addTag(ITag tag);
    boolean removeTag(ITag tag);
}
