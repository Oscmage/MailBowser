package edu.chl.mailbowser;

import edu.chl.mailbowser.account.BackgroundFetcher;
import edu.chl.mailbowser.account.IBackgroundFetcher;
import edu.chl.mailbowser.account.handlers.AccountHandler;
import edu.chl.mailbowser.account.handlers.IAccountHandler;
import edu.chl.mailbowser.contact.ContactBook;
import edu.chl.mailbowser.contact.IContactBook;
import edu.chl.mailbowser.tag.handlers.ITagHandler;
import edu.chl.mailbowser.tag.handlers.TagHandler;

/**
 * Created by OscarEvertsson on 19/05/15.
 */
public enum MainHandler {
    INSTANCE;

    private ITagHandler tagHandler = new TagHandler();
    private IAccountHandler accountHandler = new AccountHandler();
    private IContactBook contactBook = new ContactBook();
    private IBackgroundFetcher backgroundFetching = new BackgroundFetcher(accountHandler);

    public ITagHandler getTagHandler(){
        return this.tagHandler;
    }

    public IAccountHandler getAccountHandler() {
        return this.accountHandler;
    }

    public IContactBook getContactBook() {
        return this.contactBook;
    }

    public IBackgroundFetcher getBackgroundFetching() {
        return this.backgroundFetching;
    }

    /**
     * Saves all handlers that need to be saved by the program.
     *
     * This method tells each handler to save it's components.
     */
    public void saveComponents() {
        tagHandler.writeTags("Tags.ser");
        accountHandler.writeAccounts("Accounts.ser");
        //contactBook.writeContacts("Contacts.ser");
    }

    /**
     * Loads all handlers that need to be loaded by the program.
     *
     * This method tells each handler to load it's components.
     */
    public void loadComponents() {
        tagHandler.readTags("Tags.ser");
        accountHandler.readAccounts("Accounts.ser", tagHandler);
        //contactBook.readContacts("Contacts.ser");
    }
}
