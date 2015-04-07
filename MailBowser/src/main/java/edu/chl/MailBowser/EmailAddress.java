package edu.chl.MailBowser;

/**
 * Created by OscarEvertsson on 07/04/15.
 */
public class EmailAddress {
    private String address;

    /**
     * Creates an EmailAddress object with the specified address.
     * @param address specifies the Email-address.
     */
    public EmailAddress(String address){
        this.address = address.trim();
    }

    /**
     * Sets the address to the given string.
     * @param address specifies the Email-Address.
     */
    public void setAddress(String address){
        this.address = address.trim();
    }

    /**
     * Returns a string for the address.
     * @return returns a string of the address.
     */
    public String getAddress(){
        return address;
    }

    /**
     * Validates the current address.
     * @return returns true if it's ok and false if it's not ok.
     */
    public boolean isValid(){
        for(int i=0;i<address.length();i++){
            if(address.charAt(i) == '@'){
                return true;
            }
        }
        return false;
    }

}
