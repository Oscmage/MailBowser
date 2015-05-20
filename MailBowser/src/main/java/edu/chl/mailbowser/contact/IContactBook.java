package edu.chl.mailbowser.contact;

import java.util.SortedSet;

/**
 * Created by OscarEvertsson on 20/05/15.
 */
public interface IContactBook {

    void addContact(IContact contact);
    void removeContact(IContact contact);
    SortedSet<IContact> getContacts();
    SortedSet<IContact> getMatchingContacts(String query);

}
