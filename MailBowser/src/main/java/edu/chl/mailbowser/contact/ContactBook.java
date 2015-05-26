package edu.chl.mailbowser.contact;

import edu.chl.mailbowser.event.EventBus;
import edu.chl.mailbowser.io.*;
import edu.chl.mailbowser.search.Searcher;

import javax.naming.CompositeName;
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
        return Searcher.search(this.contacts,query);
    }

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

    @Override
    public boolean writeContacts(String filename) {
        IObjectWriter<TreeSet<IContact>> objectWriter = new ObjectWriter<>();
        return objectWriter.write((TreeSet<IContact>)contacts, filename); 
    }


}
