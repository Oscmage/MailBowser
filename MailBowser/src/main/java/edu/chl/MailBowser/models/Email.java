package edu.chl.MailBowser.models;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by OscarEvertsson on 07/04/15.
 * This class represents an Email with sender, receivers, subject, content, createdDate, sentDate, lastEditedDate and isSent.
 */
public class Email implements IEmail {
    private IAddress sender;
    private List<IAddress> recipients;
    private String subject;
    private String content;
    private Date createdDate;
    private Date sentDate;
    private Date lastEditedDate;
    private boolean isSent;
    private List<ITag> tags;

    /**
     * Creates an email with the specified sender, receivers, subject and content.
     * @param receivers Sets the receivers for the email
     * @param subject Sets the subject of the email.
     * @param content Sets the content for the email.
     */
    public Email(List<IAddress> receivers, String subject, String content){
        this.isSent = false;
        this.recipients = receivers;
        this.subject = subject;
        this.content = content;

        this.tags = new ArrayList<>();
        this.createdDate = new Date();
        setLastEditedDate();
    }

    @Override
    public Message getJavaxMessage(Session session) {
        MimeMessage msg = new MimeMessage(session);
        try {
            msg.setFrom(this.sender.getJavaxAddress());
            msg.setSubject(this.subject);
            msg.setText(this.content);
            msg.setSentDate(new Date());
            msg.addRecipients(Message.RecipientType.TO,getJavaxRecipients());
        } catch (MessagingException e) {
            //TODO handle this exception.
            e.printStackTrace();
        }
        return msg;
    }

    private javax.mail.Address[] getJavaxRecipients(){
        javax.mail.Address javaxArray [] = new javax.mail.Address [recipients.size()];
        for (int i=0;i<recipients.size();i++){
            javaxArray[i] = recipients.get(i).getJavaxAddress();
        }
        return javaxArray;
    }

    public boolean isSent() {
        return isSent;
    }

    public IAddress getSender() {
        return sender;
    }

    public List<IAddress> getReceivers() {
        return recipients;
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

    public List<ITag> getTags(){
        return this.tags;
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

    public void setReceivers(List<IAddress> receivers) {
        this.recipients = receivers;
    }

    public void setSender(IAddress sender) {
        this.sender = sender;
    }

    private void setSentDate(){
        this.sentDate = new Date();
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * Adds the tag to the email.
     * @param tag
     */
    public void addTag(ITag tag){
        this.tags.add(tag);
    }

    /**
     * Sets the isSent boolean to true and gives the sentDate the current date.
     */
    public void setSent(){
        this.isSent = true;
        setSentDate();
    }

    /**
     * Removes the tag from the email.
     * @param tag
     * @return if true the tag was successfully removed, otherwise the tag didn't exist.
     */
    public boolean removeTag(ITag tag){
        return this.tags.remove(tag);
    }
}
