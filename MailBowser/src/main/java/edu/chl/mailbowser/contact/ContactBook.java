package edu.chl.mailbowser.contact;

import java.util.Set;
import java.util.TreeSet;

/**
 * Created by OscarEvertsson on 20/05/15.
 */
public class ContactBook implements IContactBook{

    private Set<IContact> contacts = new TreeSet<IContact>();

    @Override
    public void addContact(IContact contact) {
        this.contacts.add(contact);
    }

    @Override
    public void removeContact(IContact contact) {
        this.contacts.remove(contact);
    }

    @Override
    public Set<IContact> getContacts() {
        return this.contacts;
    }

    @Override
    public Set<IContact> getMatchingContacts(String query) {
        return null;
    }
}
