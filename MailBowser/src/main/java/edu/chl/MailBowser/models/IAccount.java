package edu.chl.MailBowser.models;

/**
 * Created by OscarEvertsson on 09/04/15.
 */
public interface IAccount {
    void setUsername(String username);
    void setPassword(String password);
    String getUsername();
    String getPassword();
}
