package edu.chl.mailbowser.contact;

import java.util.Set;

/**
 * Created by OscarEvertsson on 20/05/15.
 */
public interface IContactBook {

    void addContact(IContact contact);
    void removeContact(IContact contact);
    Set<IContact> getContacts();
    Set<IContact> getMatchingContacts(String query);

}
