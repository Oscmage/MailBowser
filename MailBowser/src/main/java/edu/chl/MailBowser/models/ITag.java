package edu.chl.MailBowser.models;

import java.util.List;

/**
 * Created by OscarEvertsson on 24/04/15.
 */
public interface ITag {
   void setTagName(String name);
   void addEmail(IEmail email);
   void addEmails(List<IEmail> emails);
   List<IEmail> getEmails();
   boolean removeEmail(IEmail email);
}
