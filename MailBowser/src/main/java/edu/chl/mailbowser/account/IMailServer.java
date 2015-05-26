package edu.chl.mailbowser.account;

import java.io.Serializable;

/**
 * Created by mats on 26/05/15.
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
