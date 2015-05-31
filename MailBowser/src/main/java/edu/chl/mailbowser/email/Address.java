package edu.chl.mailbowser.email;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

/**
 * Created by OscarEvertsson on 07/04/15.
 *
 * Address represents an email address. You cannot create an invalid Address obejct.
 */
public class Address implements IAddress{
    private final String address;

    //rfc822 is a standard for ARPA internet text messages
    private static final String ALLOWED_ADDRESS_TYPE = "rfc822";

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
        if (address.getType().equals(ALLOWED_ADDRESS_TYPE)) {
            this.address = ((InternetAddress)address).getAddress();
        } else {
            throw new IllegalArgumentException("Address(javax.mail.Address): supplied address is not an InternetAddress");
        }
    }

    public static boolean isValidAddress(String address) {

        if(address.equals("")) {
            return false;
        }

        try {
            //InternetAddress validates the string if invalid it throws an AddressException
            InternetAddress internetAddress = new InternetAddress(address);
            internetAddress.validate();
        } catch (AddressException e) {
            return false;
        }
        return true;
    }


    @Override
    public InternetAddress getJavaMailAddress() {
        try {
            return new InternetAddress(this.address);
        } catch (AddressException e) {
            throw new IllegalStateException("The address string is invalid");
        }
    }



    @Override
    public String toString() {
        return getString();
    }


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
    @Override
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