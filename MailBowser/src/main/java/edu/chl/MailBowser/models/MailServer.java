package edu.chl.MailBowser.models;

import com.sun.mail.smtp.SMTPTransport;
import sun.net.smtp.SmtpProtocolException;

import javax.mail.*;
import java.util.Properties;

/**
 * Created by filip on 09/04/15.
 */
public class MailServer implements IMailServer{

    @Override
    public boolean send(Email email, Account account){
        EmailAddress emailAddress = account.getEmailAddress();
        javax.mail.Address[] addresses = {emailAddress.getJavaxAddress()};
        String password = account.getPassword();
        String username = account.getUsername();
        String protocol = account.getProtocol();
        int port = account.getPort();
        String host = emailAddress.getHost();

        email.setSender(emailAddress);
        Session session = Session.getDefaultInstance(new Properties());
        URLName urlName = new URLName(protocol,host,port,null,username,password);
        SMTPTransport transport = new SMTPTransport(session, urlName);
        try{
            transport.sendMessage(email.getJavaxMessage(session),addresses);
        }catch (SendFailedException ex1){
            return false;
        }catch (MessagingException ex2){
            return false;
        }
        return true;
    }
}
