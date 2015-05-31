package edu.chl.mailbowser.contact;

import edu.chl.mailbowser.event.Event;
import edu.chl.mailbowser.event.EventBus;
import edu.chl.mailbowser.event.EventType;
import edu.chl.mailbowser.utils.io.*;
import edu.chl.mailbowser.utils.search.SetSearcher;

import java.util.Set;
import java.util.TreeSet;

/**
 * Created by OscarEvertsson on 20/05/15.
 * This class represents a contact book.
 */
public class ContactBook implements IContactBook{

    private TreeSet<IContact> contacts = new TreeSet<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public void addContact(IContact contact) {
        if(contact != null){
            if (!this.contacts.contains(contact)) {
                this.contacts.add(contact);
                EventBus.INSTANCE.publish(new Event(EventType.CONTACT_ADDED, contact));
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeContact(IContact contact) {
        if (contact != null) {
            this.contacts.remove(contact);
            EventBus.INSTANCE.publish(new Event(EventType.CONTACT_REMOVED, contact));
        }
    }

    /**
     * {@inheritDoc}
     * @return
     */
    @Override
    public Set<IContact> getContacts() {
        return new TreeSet<>(this.contacts);
    }

    /**
     * {@inheritDoc}
     * @param query
     * @return
     */
    @Override
    public Set<IContact> getMatchingContacts(String query) {
        SetSearcher<IContact> searcher = new SetSearcher<>();
        return searcher.search(this.contacts, query);
    }

    /**
     * {@inheritDoc}
     * @param filename the file to look for contacts in
     * @return
     */
    @Override
    public boolean readContacts(String filename) {
        IObjectReader<TreeSet<IContact>> objectReader = new ObjectReader<>();

        try {
            contacts = objectReader.read(filename);
            return true;
        } catch (ObjectReadException e) {
            //initiate contacts to a new empty TreeSet
            contacts = new TreeSet<>();
            return false;
        }
    }

    /**
     * {@inheritDoc}
     * @param filename the file to write the contacts to
     * @return
     */
    @Override
    public boolean writeContacts(String filename) {
        IObjectWriter<TreeSet<IContact>> objectWriter = new ObjectWriter<>();
        return objectWriter.write(contacts, filename);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ContactBook)) return false;

        ContactBook that = (ContactBook) o;

        if (contacts != null ? !contacts.equals(that.contacts) : that.contacts != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return contacts != null ? contacts.hashCode() : 0;
    }
}
