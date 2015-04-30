package edu.chl.MailBowser.models;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

/**
 * Created by OscarEvertsson on 07/04/15.
 *
 * Address represents an email address. You cannot create an invalid Address obejct.
 */
public class Address implements IAddress {
    private String address;

    /**
     * Creates an Address object with the specified String. The String is trimmed and validated upon creation.
     * If the address is invalid the position of where the address possibly could be wrong is printed out.
     *
     * @param address specifies the email address.
     */
    public Address(String address) {
        if (isValidAddress(address)) {
            this.address = address;
        } else {
            throw new IllegalArgumentException("Address(String): supplied string is not a valid email address.");
        }
    }

    /**
     * Creates an address from an existing javax.mail.Address.
     *
     * @param address the address to create a new Address object from
     */
    public Address(javax.mail.Address address) {
        if (address.getType().equals("rfc822")) {
            this.address = ((InternetAddress)address).getAddress();
        } else {
            throw new IllegalArgumentException("Address(javax.mail.Address): supplied address is not an InternetAddress");
        }
    }

    /**
     * Creates a new Address from an existing Address
     *
     * @param address the address to copy
     */
    public Address(Address address) {
        this(address.getString());
    }

    private boolean isValidAddress(String address) {
        try {
            new InternetAddress(address);
        } catch (AddressException e) {
            return false;
        }
        return true;
    }

    /**
     * @return a copy of the javax.mail.InternetAddress object which holds the information
     */
    public InternetAddress getJavaxAddress() {
        try {
            return new InternetAddress(this.address);
        } catch (AddressException e) {
            //This will never happen since the address is validated when created.
        }
        return null;
    }


    /**
     * Returns a string representation of the address, in the format user@domain.com
     *
     * @return a string representation of the address
     */
    public String toString() {
        return this.address;
    }

    /**
     * Returns a string representation of the address, in the format user@domain.com
     * @return
     */
    public String getString(){
        return this.address;
    }




    
}