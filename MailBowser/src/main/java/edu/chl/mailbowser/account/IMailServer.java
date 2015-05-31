package edu.chl.mailbowser.account;

import java.io.Serializable;

/**
 * Created by mats on 26/05/15.
 *
 * An interface for mail servers. Mail servers should be able to provide a hostname and a port.
 */
public interface IMailServer extends Serializable {
    /**
     * Returns the hostname for this mail server.
     *
     * @return the hostname for this mail server
     */
    String getHostname();

    /**
     * Returns the post for this mail server.
     *
     * @return the hostname for this mail server
     */
    String getPort();
}
