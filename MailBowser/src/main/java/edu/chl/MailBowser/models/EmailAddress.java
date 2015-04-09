package edu.chl.MailBowser.models;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

/**
 * Created by OscarEvertsson on 07/04/15.
 * EmailAddress represents an EmailAddress and have the possibility to check whether the address is correct or not.
 */
public class EmailAddress implements Address{
    private InternetAddress address;

    /**
     * Creates an EmailAddress object with the specified address trimmed(removes any space before and after the string).
     * If the address is invalid the position of where the address possibly could be wrong is printed out.
     * @param address specifies the Email-address.
     */
    public EmailAddress(String address){
        try {
            this.address = new InternetAddress(address.trim());
        } catch (AddressException e) {
            System.out.println(e.getPos()); //SUPPOSED TO DO SOMETHING LATER WHEN VIEW IS AVAILABLE (remove this when fixed).
        }
    }

    /**
     * Sets the address to the given string also removes any space before and after the given string.
     * @param address specifies the Email-Address.
     */
    public void setAddress(String address){
        this.address.setAddress(address.trim());
    }

    /**
     * Returns a string for the address.
     * @return returns a string of the address.
     */
    public String getAddress(){
        return this.address.getAddress();
    }

    /**
     * Validates the address.
     * @return returns -1 if the string is valid otherwise returns the position of where the error was detected.
     */
    public int isValid(){
        try {
            this.address.validate();
        } catch (AddressException e) {
            return e.getPos();
        }
        return -1;
    }

}
