package edu.chl.mailbowser.email;

import javax.mail.*;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by OscarEvertsson on 07/04/15.
 * This class represents an Email with sender, receivers, subject, content, sentDate and isSent.
 *
 */
public class Email implements IEmail {
    private static final long serialVersionUID = 2832405631172419366L;

    private IAddress sender;
    private final String subject;
    private final String content;

    private List<? extends IAddress> to = new ArrayList<>();
    private List<? extends IAddress> cc = new ArrayList<>();
    private List<? extends IAddress> bcc = new ArrayList<>();

    private Date sentDate;
    private Date receivedDate;

    /**
     * A class for constructing Email objects.
     */
    public static class Builder {
        // mandatory fields
        private final String subject;
        private final String content;

        private Date sentDate;
        private Date receivedDate;

        // optional fields
        private IAddress sender = null;
        private List<? extends IAddress> to = new ArrayList<>();
        private List<? extends IAddress> cc = new ArrayList<>();
        private List<? extends IAddress> bcc = new ArrayList<>();

        /**
         * Builder constructor initiates the builder with the fields mandatory when creating an email
         * @param subject is a mandatory field used to build email
         * @param content is a mandatory field used to build email
         */
        public Builder(String subject, String content) {
            this.subject = (subject == null) ? "" : subject;
            this.content = (content == null) ? "" : content;

            this.sentDate = new Date();
            this.receivedDate = new Date();
        }

        public Builder sender(IAddress val) {
            this.sender = val;
            return this;
        }

        public Builder to(List<? extends IAddress> val) {
            this.to = val;
            return this;
        }

        public Builder cc(List<? extends IAddress> val) {
            this.cc = val;
            return this;
        }

        public Builder bcc(List<? extends IAddress> val) {
            this.bcc = val;
            return this;
        }

        /**
         * Buildes a email with this builder
         * @return an {@link edu.chl.mailbowser.email.Email} built with the fields of this builder
         */
        public Email build() {
            return new Email(this);
        }
    }

    /**
     * Creates a new email from a Builder object.
     *
     * @param builder the builder to use when creating the new email object
     */
    private Email(Builder builder) {
        this.subject = builder.subject;
        this.content = builder.content;
        this.sender = builder.sender;

        this.sentDate = builder.sentDate;
        this.receivedDate = builder.receivedDate;

        this.to = builder.to;
        this.cc = builder.cc;
        this.bcc = builder.bcc;
    }

    /**
     * Creates an email from an existing javax.mail.Message.
     *
     * @param message the message to create a new Email object from
     * @throws IllegalArgumentException when message is null, or if the message is invalid
     */
    public Email(Message message) throws IllegalArgumentException {
        if (message == null) {
            throw new IllegalArgumentException("Can't create an email from a null message");
        }

        try {
            // set subject and content
            String subject = message.getSubject();
            this.subject = (subject == null) ? "" : subject;

            String content = recursiveGetText(message);
            this.content = (content == null) ? "" : content;

            // set recipients
            this.to = getRecipients(message, Message.RecipientType.TO);
            this.cc = getRecipients(message, Message.RecipientType.CC);
            this.bcc = getRecipients(message, Message.RecipientType.BCC);

            // set sender. if there are none, throw an exception
            javax.mail.Address[] senders = message.getFrom();
            if (senders == null || senders.length == 0) {
                throw new MessagingException("The message doesn't have any senders");
            }
            this.sender = new Address(senders[0]);

            // set dates
            Date sentDate = message.getSentDate();
            if (sentDate == null) {
                this.sentDate = new Date();
            } else {
                this.sentDate = message.getSentDate();
            }

            Date receivedDate = message.getReceivedDate();
            if (receivedDate == null) {
                this.receivedDate = new Date();
            } else {
                this.receivedDate = receivedDate;
            }
        } catch (MessagingException e) {
            throw new IllegalArgumentException("Invalid message: " + e.getMessage());
        }
    }

    /**
     * Takes a JavaMail message and a recipient type and returns all the recipients of that type in a list.
     *
     * @param message the message to get recipients from
     * @param type the type of recipients to get
     * @return a list containing all the recipients of a specific type
     * @throws MessagingException when an error occurs while getting the recipients from the message
     */
    private List<IAddress> getRecipients(Message message, Message.RecipientType type) throws MessagingException {
        List<IAddress> recipients = new ArrayList<>();

        javax.mail.Address[] addressArray = message.getRecipients(type);
        if (addressArray == null) {
            return new ArrayList<>();
        } else {
            for (javax.mail.Address address : addressArray) {
                recipients.add(new Address(address));
            }
        }

        return recipients;
    }

    /**
     * Recursively works through all parts of a message, and look for the content.
     * This implementation is copied from JavaMail API:s FAQ-page
     *
     * @param p the message part to start from
     * @return the content of a message. If something goes wrong, an empty string is returned.
     */
    private static String recursiveGetText(Part p) {
        try {
            // if the mime type is text/*, just get the content of the part an return it.
            if (p.isMimeType("text/*")) {
                String s = (String) p.getContent();
                return s;
            }

            // if the mime type is multipart/alternative, the part contains other parts. call recursiveGetText on each
            // of the parts contained in the part. prefer text/html before text/plain.
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
        } catch (IOException | MessagingException e) {
            e.printStackTrace();
            // if any error occurs, just return an empty string
            return "";
        }

        return "";
    }


    @Override
    public Message getJavaMailMessage(Session session) {
        MimeMessage msg = new MimeMessage(session);
        try {
            msg.setFrom(this.sender.getJavaMailAddress());
            msg.setSubject(this.subject);
            msg.setContent(this.content, "text/html; charset=utf-8");
            msg.setSentDate(this.sentDate);

            msg.addRecipients(Message.RecipientType.TO, convertToJavaMailAddresses(to));
            msg.addRecipients(Message.RecipientType.CC, convertToJavaMailAddresses(cc));
            msg.addRecipients(Message.RecipientType.BCC, convertToJavaMailAddresses(bcc));
        } catch (MessagingException e) {
            throw new IllegalArgumentException("Invalid session");
        }
        return msg;
    }

    /**
     * Takes a list of addresses and returns an array of javamail addresses
     * @param addresses
     * @return
     */
    private static javax.mail.Address[] convertToJavaMailAddresses(List<? extends IAddress> addresses) {
        List<javax.mail.Address> converted = new ArrayList<>();

        // loop through all addresses, convert them to JavaMail addresses, and add them to the converted list
        // if they aren't null
        for (IAddress address : addresses) {
            javax.mail.Address convertedAddress = address.getJavaMailAddress();
            if (convertedAddress != null) {
                converted.add(convertedAddress);
            }
        }

        return converted.toArray(new javax.mail.Address[converted.size()]);
    }

    @Override
    public IAddress getSender() {
        return sender;
    }

    @Override
    public List<IAddress> getTo() {
        return new ArrayList<>(this.to);
    }

    @Override
    public List<IAddress> getCc() {
        return new ArrayList<>(this.cc);
    }

    @Override
    public List<IAddress> getBcc() {
        return new ArrayList<>(this.bcc);
    }

    @Override
    public List<IAddress> getAllRecipients() {
        List<IAddress> recipients = new ArrayList<>();
        recipients.addAll(getTo());
        recipients.addAll(getCc());
        recipients.addAll(getBcc());
        return recipients;
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public String getSubject() {
        return subject;
    }

    @Override
    public Date getSentDate() {
        return (Date)sentDate.clone();
    }

    @Override
    public Date getReceivedDate(){
        return (Date)this.receivedDate.clone();
    }

    @Override
    public void setSender(IAddress sender) {
        this.sender = sender;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Email)) return false;

        Email email = (Email) o;
        //TODO: if not sent sendDate and receiveDate
        if (content != null ? !content.equals(email.content) : email.content != null) return false;
        if (receivedDate != null ? !receivedDate.equals(email.receivedDate) : email.receivedDate != null) return false;
        if (to != null ? !to.equals(email.to) : email.to != null) return false;
        if (cc != null ? !cc.equals(email.cc) : email.cc != null) return false;
        if (bcc != null ? !bcc.equals(email.bcc) : email.bcc != null) return false;
        if (sender != null ? !sender.equals(email.sender) : email.sender != null) return false;
        if (sentDate != null ? !sentDate.equals(email.sentDate) : email.sentDate != null) return false;
        if (subject != null ? !subject.equals(email.subject) : email.subject != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = sender != null ? sender.hashCode() : 0;
        result = 31 * result + (to != null ? to.hashCode() : 0);
        result = 31 * result + (cc != null ? cc.hashCode() : 0);
        result = 31 * result + (bcc != null ? bcc.hashCode() : 0);
        result = 31 * result + (subject != null ? subject.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (sentDate != null ? sentDate.hashCode() : 0);
        result = 31 * result + (receivedDate != null ? receivedDate.hashCode() : 0);
        return result;
    }

    /**
     * Checks whether or not this email matches a given string.
     *
     * @param query the string to match against
     * @return true if the email's content, subject, sender or recipients contains the query
     */
    @Override
    public boolean matches(String query) {
        if (query == null) {
            return false;
        }

        query = query.toLowerCase();

        return (subject != null && subject.toLowerCase().contains(query))
                || (content != null && content.toLowerCase().contains(query))
                || (sender != null && sender.matches(query))
                || checkIfAnyAddressMatchesQuery(query, to)
                || checkIfAnyAddressMatchesQuery(query, cc)
                || checkIfAnyAddressMatchesQuery(query, bcc);
    }

    /**
     * Checks if any address in a list of addresses matches a given query.
     *
     * @param query the query to match against
     * @param addresses the list of addresses to search in
     * @return true if any of the addresses matches the given query, otherwise false
     */
    private static boolean checkIfAnyAddressMatchesQuery(String query, List<? extends IAddress> addresses) {
        for (IAddress address : addresses) {
            if (address.matches(query)) {
                return true;
            }
        }
        return false;
    }

    /**
     * {@inheritDoc}
     *
     * Emails are compared in this order:
     * * receivedDate, newest first
     * * sentDate, newest first
     * * subject, alphabetical order
     * * content, alphabetical order
     * * sender
     * * to
     * * cc
     * * bcc
     *
     * If all the above are the same, this method returns 0.
     */
    @Override
    public int compareTo(IEmail o) {
        int receivedDateComparison = this.receivedDate.compareTo(o.getReceivedDate());
        if (receivedDateComparison != 0) {
            return -receivedDateComparison;
        }

        int sentDateComparison = this.sentDate.compareTo(o.getSentDate());
        if (sentDateComparison != 0) {
            return -sentDateComparison;
        }

        int subjectComparison = this.subject.compareTo(o.getSubject());
        if (subjectComparison != 0) {
            return subjectComparison;
        }

        int contentComparison = this.content.compareTo(o.getContent());
        if (contentComparison != 0) {
            return contentComparison;
        }

        int senderComparison = this.sender.compareTo(o.getSender());
        if (senderComparison != 0) {
            return senderComparison;
        }

        if (!this.to.equals(o.getTo())) {
            return -1;
        }

        if (!this.cc.equals(o.getCc())) {
            return -1;
        }

        if (!this.bcc.equals(o.getBcc())) {
            return -1;
        }

        return 0;
    }

    @Override
    public String toString() {
        return this.subject;
    }
}
