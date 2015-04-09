package edu.chl.MailBowser.models;

/**
 * Created by OscarEvertsson on 09/04/15.
 * The class Account stores the EmailAddress, username, password and Mail-server for a specific Email-account.
 */
public class Account implements IAccount {
    private EmailAddress address;
    private String username;
    private String password;
    private MailServer server;

    /**
     * Constructs a Account with the specified EmailAddress, username, password and server.
     * @param address
     * @param username
     * @param password
     * @param server
     */
    public Account(EmailAddress address, String username, String password, MailServer server){
        this.address = address;
        this.username = username;
        this.password = password;
        this.server = server;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public void setServer(MailServer server){
        this.server = server;
    }

    public void setEmailAddress(EmailAddress address){
        this.address = address;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    public MailServer getOutgoingServer(){
        return this.server;
    }

    public EmailAddress getEmailAddress(){
        return this.address;
    }
}
