package edu.chl.MailBowser.models;

import java.util.Date;
import java.util.List;

/**
 * Created by OscarEvertsson on 07/04/15.
 */
public class Email {
    private EmailAddress sender;
    private List<EmailAddress> receivers;
    private String subject;
    private String content;
    private Date createdDate;
    private Date sentDate;
    private Date lastEditedDate;
    private boolean isSent;

    public Email(EmailAddress sender, List<EmailAddress> receivers, String subject, String content){
        this.createdDate = new Date();
        this.isSent = false;
        this.sender = sender;
        this.receivers = receivers;
        this.subject = subject;
        this.content = content;
    }

    public boolean isSent() {
        return isSent;
    }

    public EmailAddress getSender() {
        return sender;
    }

    public List<EmailAddress> getReceivers() {
        return receivers;
    }

    public String getContent() {
        return content;
    }

    public String getSubject() {
        return subject;
    }

    public Date getSentDate() {
        return (Date)sentDate.clone();
    }

    public Date getLastEditedDate() {
        return (Date)lastEditedDate.clone();
    }

    public Date getCreatedDate() {
        return (Date)createdDate.clone();
    }

    public void setSentDate(){
        this.sentDate = new Date();
    }

    public void setLastEditedDate(){
        this.lastEditedDate = new Date();
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public void setLastEditedDate(Date lastEditedDate) {
        this.lastEditedDate = lastEditedDate;
    }

    public void setReceivers(List<EmailAddress> receivers) {
        this.receivers = receivers;
    }

    public void setSender(EmailAddress sender) {
        this.sender = sender;
    }

    public void setSentDate(Date sentDate) {
        this.sentDate = sentDate;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setSent(){
        this.isSent = true;
    }

}
