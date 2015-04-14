package edu.chl.MailBowser.models;

import javax.mail.Session;
import java.util.Date;
import java.util.List;

/**
 * Created by OscarEvertsson on 10/04/15.
 */
public interface IEmail {
    javax.mail.Message getJavaxMessage(Session session);
    public boolean isSent();
    public IAddress getSender();
    public List<IAddress> getReceivers();
    public String getContent();
    public String getSubject();
    public Date getSentDate();
    public Date getLastEditedDate();
    public Date getCreatedDate();
    public void setLastEditedDate();
    public void setContent(String content);
    public void setCreatedDate(Date createdDate);
    public void setLastEditedDate(Date lastEditedDate);
    public void setReceivers(List<IAddress> receivers);
    public void setSender(IAddress sender);
    public void setSubject(String subject);
    public void setSent();
}
