package edu.chl.mailbowser.main;

import edu.chl.mailbowser.account.AccountHandler;
import edu.chl.mailbowser.account.IAccountHandler;
import edu.chl.mailbowser.contact.ContactBook;
import edu.chl.mailbowser.contact.IContactBook;
import edu.chl.mailbowser.tag.ITagHandler;
import edu.chl.mailbowser.tag.TagHandler;

/**
 * Created by OscarEvertsson on 19/05/15.
 * MainHandler is a enum singleton to keep track of every initiated handler in the application.
 * It's in the MainHandler you retrieve access to the other handlers of the application.
 */
public enum MainHandler {
    INSTANCE;

    private ITagHandler tagHandler = new TagHandler();
    private IAccountHandler accountHandler = new AccountHandler();
    private IContactBook contactBook = new ContactBook();

    public ITagHandler getTagHandler(){
        return this.tagHandler;
    }

    public IAccountHandler getAccountHandler() {
        return this.accountHandler;
    }

    public IContactBook getContactBook() {
        return this.contactBook;
    }


    /**
     * Saves all handlers that need to be saved by the program.
     *
     * This method tells each handler to save it's components.
     */
    public void saveComponents() {
        tagHandler.writeTags("Tags.ser");
        accountHandler.writeAccounts("Accounts.ser");
        contactBook.writeContacts("Contacts.ser");
    }

    /**
     * Loads all handlers that need to be loaded by the program.
     *
     * This method tells each handler to load it's components.
     */
    public void loadComponents() {
        tagHandler.readTags("Tags.ser");
        accountHandler.readAccounts("Accounts.ser", tagHandler);
        contactBook.readContacts("Contacts.ser");
    }
}
