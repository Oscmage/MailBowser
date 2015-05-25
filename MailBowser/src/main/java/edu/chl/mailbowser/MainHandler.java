package edu.chl.mailbowser;

import edu.chl.mailbowser.account.AccountHandler;
import edu.chl.mailbowser.account.IAccountHandler;
import edu.chl.mailbowser.contact.ContactBook;
import edu.chl.mailbowser.contact.IContactBook;
import edu.chl.mailbowser.tag.handlers.ITagHandler;
import edu.chl.mailbowser.tag.handlers.TagHandler;

/**
 * Created by OscarEvertsson on 19/05/15.
 */
public enum MainHandler {
    INSTANCE;

    private static final int FETCH_INTERVAL = 30000;

    private ITagHandler tagHandler = new TagHandler();
    private IAccountHandler accountHandler = new AccountHandler();
    private IContactBook contactBook = new ContactBook();
    private IBackgroundFetcher backgroundFetcher = new BackgroundFetcher(FETCH_INTERVAL, accountHandler);

    public ITagHandler getTagHandler(){
        return this.tagHandler;
    }

    public IAccountHandler getAccountHandler() {
        return this.accountHandler;
    }

    public IContactBook getContactBook() {
        return this.contactBook;
    }

    public IBackgroundFetcher getBackgroundFetcher() {
        return this.backgroundFetcher;
    }
}
