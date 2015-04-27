package edu.chl.MailBowser.models;

import javax.mail.*;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by OscarEvertsson on 07/04/15.
 * This class represents an Email with sender, receivers, subject, content, createdDate, sentDate, lastEditedDate and isSent.
 */
public class Email implements IEmail {
    // TODO: add separate lists for the different recipient types: TO, CC, BCC. Constructors must also be updated.

    private IAddress sender;
    private List<IAddress> recipients = new ArrayList<>();
    private String subject;
    private String content;
    private Date createdDate;
    private Date sentDate;
    private Date receivedDate;
    private Date lastEditedDate;
    private boolean isSent;

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

        this.createdDate = new Date();
        setLastEditedDate();
    }

    /**
     * Creates an email from an existing javax.mail.Message.
     *
     * @param message the message to create a new Email object from
     */
    public Email(Message message) {
        try {
            this.isSent = false;

            // add recipients
            javax.mail.Address[] recipients = message.getAllRecipients();
            for (javax.mail.Address recipient : recipients) {
                this.recipients.add(new Address(recipient));
            }

            // set subject and content
            this.subject = message.getSubject();
            this.content = recursiveGetText(message);

            // set dates
            this.sentDate = message.getSentDate();
            this.receivedDate = message.getReceivedDate();
        } catch (MessagingException | IOException e) {
            throw new IllegalArgumentException();
        }
    }

    private static String recursiveGetText(Part p) throws MessagingException, IOException {
        if (p.isMimeType("text/*")) {
            String s = (String) p.getContent();
            return s;
        }

        if (p.isMimeType("multipart/alternative")) {
            // prefer html text over plain text
            Multipart mp = (Multipart) p.getContent();
            String text = null;
            for (int i = 0; i < mp.getCount(); i++) {
                Part bp = mp.getBodyPart(i);
                if (bp.isMimeType("text/plain")) {
                    if (text == null) {
                        text = recursiveGetText(bp);
                    }
                } else if (bp.isMimeType("text/html")) {
                    String s = recursiveGetText(bp);
                    if (s != null) {
                        return s;
                    }
                } else {
                    return recursiveGetText(bp);
                }
            }
            return text;
        } else if (p.isMimeType("multipart/*")) {
            Multipart mp = (Multipart) p.getContent();
            for (int i = 0; i < mp.getCount(); i++) {
                String s = recursiveGetText(mp.getBodyPart(i));
                if (s != null)
                    return s;
            }
        }

        return null;
    }

    /**
     * Returns this email represented as a javax.mail.Message object.
     *
     * @param session the session to use when creating the new message object
     * @return an javax.mail.Message object representing this email.
     */
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
     * Sets the isSent boolean to true and gives the sentDate the current date.
     */
    public void setSent(){
        this.isSent = true;
        setSentDate();
    }

}
