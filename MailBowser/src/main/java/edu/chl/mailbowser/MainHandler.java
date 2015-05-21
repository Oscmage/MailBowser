package edu.chl.mailbowser;

import edu.chl.mailbowser.account.BackgroundFetching;
import edu.chl.mailbowser.account.IBackgroundFetching;
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
    private IBackgroundFetching backgroundFetching = new BackgroundFetching(accountHandler);

    public ITagHandler getTagHandler(){
        return this.tagHandler;
    }

    public IAccountHandler getAccountHandler() {
        return this.accountHandler;
    }

    public IContactBook getContactBook() {
        return this.contactBook;
    }

    public IBackgroundFetching getBackgroundFetching() {
        return this.backgroundFetching;
    }
}
