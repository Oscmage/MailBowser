package edu.chl.mailbowser.contact;

import edu.chl.mailbowser.email.models.IAddress;

import java.util.List;

/**
 * Created by jesper on 2015-05-20.
 */
public interface IContact extends Comparable {
    String getFullName();
    String getFirstName();
    String getLastName();
    void setFirstName(String firstName);
    void setLastName(String lastName);
    List<IAddress> getEmailAddress();
}
