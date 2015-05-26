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

    /**
     * Returns this email represented as a javax.mail.Message object.
     *
     * @param session the session to use when creating the new message object
     * @return an javax.mail.Message object representing this email.
     */
    javax.mail.Message getJavaMailMessage(Session session);

    /**
     * Returns the email address of the sender
     * @return sender address as a {@link IAddress}
     */
    IAddress getSender();

    /**
     * Return the email addresses that are set as recipients
     * @return a list of {@link edu.chl.mailbowser.email.IAddress} which contains the recipients
     */
    List<IAddress> getTo();

    /**
     * Return the email addresses that receive a copy of the email
     * @return a list of {@link edu.chl.mailbowser.email.IAddress} which contains the copy-recipients
     */
    List<IAddress> getCc();

    /**
     * Return the email addresses that receive a blind copy of the email
     * @return a list of {@link edu.chl.mailbowser.email.IAddress} which contains the blind copy-recipients
     */
    List<IAddress> getBcc();

    /**
     * Returns a list of all recipients of this email
     * @return a list of {@link edu.chl.mailbowser.email.IAddress} which contains all recipients of this email
     */
    List<IAddress> getAllRecipients();

    /**
     * Returns the content of this email
     * @return the content as a string
     */
    String getContent();

    /**
     * Returns the subject of this email
     * @return the subject as a string
     */
    String getSubject();

    /**
     * Returns the sent date of this object
     * @return the sent date as a {@link java.util.Date}
     */
    Date getSentDate();

    /**
     * Returns the received date of this object
     * @return the received date as a {@link java.util.Date}
     */
    Date getReceivedDate();

    /**
     * Sets the content of the email
     * @param content string that current content is set to
     */
    void setContent(String content);

    /**
     * Sets sender of the email
     * @param sender string that current sender is set to
     */
    void setSender(IAddress sender);
}
