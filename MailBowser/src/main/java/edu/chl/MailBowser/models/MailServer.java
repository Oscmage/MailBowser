package edu.chl.MailBowser.models;

import java.util.Properties;

/**
 * Created by filip on 09/04/15.
 */
public abstract class MailServer{
    private String hostname;
    private String port;

    /**
     * Creates a new MailServer with the specified hostname and port.
     *
     * @param hostname
     * @param port
     */
    public MailServer(String hostname, String port) {
        this.hostname = hostname;
        this.port = port;
    }

    /**
     * Returns the hostname for this mail server.
     *
     * @return hostname
     */
    public String getHostname(){
        return hostname;
    }

    /**
     * Returns the port for this mail server.
     *
     * @return port
     */
    public String getPort(){
        return port;
    }
}
