package edu.chl.mailbowser.email.models;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.io.Serializable;

/**
 * Created by OscarEvertsson on 07/04/15.
 *
 * Address represents an email address. You cannot create an invalid Address obejct.
 */
public class Address implements IAddress{
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
    @Override
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
    @Override
    public String toString() {
        return this.address;
    }

    /**
     * Returns a string representation of the address, in the format user@domain.com
     * @return
     */
    @Override
    public String getString(){
        return this.address;
    }

    @Override
    public boolean equals(Object o){
        if (o == this) {
            return true;
        } else if (o == null) {
            return false;
        } else if (!this.getClass().equals(o.getClass())) {
            return false;
        }
        Address a = (Address)o;
        return a.getString().equals(this.address);
    }

    /**
     * Returns a hashcode for the object.
     * @return
     */
    public int hashCode(){
        return address.hashCode()*13;
    }

    /**
     * Checks whether or not this address matches a given string.
     *
     * @param query the string to match against
     * @return true if the address contains the given query
     */
    @Override
    public boolean matches(String query) {
        return query != null && address.contains(query);
    }
}