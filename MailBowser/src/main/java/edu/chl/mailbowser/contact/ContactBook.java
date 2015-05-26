package edu.chl.mailbowser.contact;

import edu.chl.mailbowser.io.*;
import edu.chl.mailbowser.search.Searcher;

import java.util.Set;
import java.util.TreeSet;

/**
 * Created by OscarEvertsson on 20/05/15.
 * This class represents a contact book.
 */
public class ContactBook implements IContactBook{

    private Set<IContact> contacts = new TreeSet<>();

    /**
     * {@inheritDoc}
     * @throws IllegalArgumentException if contact is null
     */
    @Override
    public void addContact(IContact contact) {
        if(contact == null){
            throw new IllegalArgumentException("Contact can't be null");
        }
        this.contacts.add(contact);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeContact(IContact contact) {
        this.contacts.remove(contact);
    }

    /**
     * {@inheritDoc}
     * @return
     */
    @Override
    public Set<IContact> getContacts() {
        return this.contacts;
    }

    /**
     * {@inheritDoc}
     * @param query
     * @return
     */
    @Override
    public Set<IContact> getMatchingContacts(String query) {
        return Searcher.search(this.contacts,query);
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
        return objectWriter.write((TreeSet<IContact>)contacts, filename); 
    }


}
