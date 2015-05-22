package edu.chl.mailbowser.email.models;

import javax.mail.*;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by OscarEvertsson on 07/04/15.
 * This class represents an Email with sender, receivers, subject, content, createdDate, sentDate, lastEditedDate and isSent.
 */
public class Email implements IEmail {
    private IAddress sender;
    private String subject;
    private String content;

    private List<IAddress> to = new ArrayList<>();
    private List<IAddress> cc = new ArrayList<>();
    private List<IAddress> bcc = new ArrayList<>();

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
        private List<IAddress> to = new ArrayList<>();
        private List<IAddress> cc = new ArrayList<>();
        private List<IAddress> bcc = new ArrayList<>();

        public Builder(String subject, String content) {
            this.subject = subject;
            this.content = content;

            this.sentDate = new Date();
            this.receivedDate = new Date();
        }

        public Builder sender(IAddress val) {
            this.sender = val;
            return this;
        }

        public Builder to(List<IAddress> val) {
            this.to = val;
            return this;
        }

        public Builder cc(List<IAddress> val) {
            this.cc = val;
            return this;
        }

        public Builder bcc(List<IAddress> val) {
            this.bcc = val;
            return this;
        }

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
     * Constructor for testing the email search function.
     *
     * @param subject
     * @param content
     * @param sender
     * @param to
     */
    public Email(String subject, String content, IAddress sender, List<IAddress> to) {
        this.subject = subject;
        this.content = content;
        this.sender = sender;
        this.to = to;
    }

    /**
     * Creates an email from an existing javax.mail.Message.
     *
     * @param message the message to create a new Email object from
     */
    public Email(Message message) {
        try {
            // add to
            javax.mail.Address[] to = message.getRecipients(Message.RecipientType.TO);
            if (to != null) {
                for (javax.mail.Address recipient : to) {
                    this.to.add(new Address(recipient));
                }
            }

            // add cc
            javax.mail.Address[] cc = message.getRecipients(Message.RecipientType.CC);
            if (cc != null) {
                for (javax.mail.Address recipient : cc) {
                    this.cc.add(new Address(recipient));
                }
            }

            // add bcc
            javax.mail.Address[] bcc = message.getRecipients(Message.RecipientType.BCC);
            if (bcc != null) {
                for (javax.mail.Address recipient : bcc) {
                    this.bcc.add(new Address(recipient));
                }
            }

            // set subject, content and from
            this.subject = message.getSubject();
            try {
                this.content = recursiveGetText(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.sender = new Address(message.getFrom()[0]);

            // set dates
            this.sentDate = message.getSentDate();
            this.receivedDate = message.getReceivedDate();
        } catch (MessagingException e) {
            throw new IllegalArgumentException("Email(Message): An error occurred while reading the message");
        }
    }

    /**
     * Recursively works through all parts of a message, and look for the content.
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

    /**
     * Returns this email represented as a javax.mail.Message object.
     *
     * @param session the session to use when creating the new message object
     * @return an javax.mail.Message object representing this email.
     */
    @Override
    public Message getJavaMailMessage(Session session) {
        MimeMessage msg = new MimeMessage(session);
        try {
            msg.setFrom(this.sender.getJavaMailAddress());
            msg.setSubject(this.subject);
            msg.setContent(this.content, "text/html; charset=utf-8");
            msg.setSentDate(new Date());

            // add recipients of all three different types
            msg.addRecipients(Message.RecipientType.TO, getJavaxRecipients(to));
            msg.addRecipients(Message.RecipientType.CC, getJavaxRecipients(cc));
            msg.addRecipients(Message.RecipientType.BCC, getJavaxRecipients(bcc));
        } catch (MessagingException e) {
            // TODO handle this exception.
            throw new IllegalArgumentException("getJavaMailMessage: f");
        }
        return msg;
    }

    /**
     * Takes a list of addresses and returns an array of javamail addresses
     * @param addresses
     * @return
     */
    private static javax.mail.Address[] getJavaxRecipients(List<IAddress> addresses) {
        List<javax.mail.Address> recipients = addresses
                .stream()
                .map(IAddress::getJavaMailAddress)
                .collect(Collectors.toList());

        // create an array of the list of addresses
        return recipients.toArray(new javax.mail.Address[addresses.size()]);
    }

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
        recipients.addAll(to);
        recipients.addAll(cc);
        recipients.addAll(bcc);
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

    public Date getReceivedDate(){
        return (Date)this.receivedDate.clone();
    }


    public void setContent(String content) {
        this.content = content;
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

        return subject.toLowerCase().contains(query)
                || (content != null && content.toLowerCase().contains(query))
                || sender.matches(query)
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
    private static boolean checkIfAnyAddressMatchesQuery(String query, List<IAddress> addresses) {
        for (IAddress address : addresses) {
            if (address.matches(query)) {
                return true;
            }
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int compareTo(IEmail o) {
        return o.getReceivedDate().compareTo(this.getReceivedDate());
    }
}
