package edu.chl.MailBowser.models;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

/**
 * Created by OscarEvertsson on 07/04/15.
 * EmailAddress represents an EmailAddress and have the possibility to check whether the address is correct or not.
 */
public class Address extends AbstractModel implements IAddress{
    private InternetAddress address;

    /**
     * Creates an EmailAddress object with the specified address trimmed(removes any space before and after the string).
     * If the address is invalid the position of where the address possibly could be wrong is printed out.
     * @param address specifies the Email-address.
     */
    public Address(String address){
        try {
            this.address = new InternetAddress(address.trim());
        } catch (AddressException e) {
            System.out.println(e.getPos()); //SUPPOSED TO DO SOMETHING LATER WHEN VIEW IS AVAILABLE (remove this when fixed).
        }
    }

    /** Creates a new EmailAddress from an existing EmailAddress
     *
     * @param emailAddress the address
     */
    public Address(Address emailAddress) {
        this(emailAddress.toString());
    }

    /**
     * @return Gives a copy of the javax.mail.InternetAddress object which holds the information
     */
    public InternetAddress getJavaxAddress() {
        return (InternetAddress)this.address.clone();
    }


    /**
     * Returns a string for the address.
     * @return returns a string of the address.
     */
    public String toString(){
        return this.address.toString();
    }
}