package edu.chl.mailbowser;

import edu.chl.mailbowser.account.handlers.AccountHandler;
import edu.chl.mailbowser.account.handlers.IAccountHandler;
import edu.chl.mailbowser.tag.handlers.ITagHandler;
import edu.chl.mailbowser.tag.handlers.TagHandler;

/**
 * Created by OscarEvertsson on 19/05/15.
 */
public enum MainHandler {
    INSTANCE;

    private ITagHandler tagHandler = new TagHandler();
    private IAccountHandler accountHandler= new AccountHandler();

    public ITagHandler getTagHandler(){
        return this.tagHandler;
    }

    public IAccountHandler getAccountHandler() {
        return this.accountHandler;
    }
}
