package edu.chl.mailbowser.account.handlers;

import edu.chl.mailbowser.account.models.IAccount;
import edu.chl.mailbowser.email.models.IEmail;
import edu.chl.mailbowser.event.Event;
import edu.chl.mailbowser.event.EventBus;
import edu.chl.mailbowser.event.EventType;
import edu.chl.mailbowser.io.*;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mats on 11/05/15.
 *
 * A concrete implementation of IAccountHandler. This particular implementation stores the accounts in a list.
 */
public class AccountHandler implements IAccountHandler{
    private List<IAccount> accounts = new ArrayList<>();

    private Cipher cipher = null;

    /**
     * {@inheritDoc}
     */
    @Override
    public void addAccount(IAccount account) {
        accounts.add(account);
        EventBus.INSTANCE.publish(new Event(EventType.ADD_ACCOUNT, account));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeAccount(IAccount account) {
        accounts.remove(account);
        EventBus.INSTANCE.publish(new Event(EventType.REMOVE_ACCOUNT, account));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<IAccount> getAccounts() {
        return new ArrayList<>(accounts);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<IEmail> getAllEmails() {
        List<IEmail> emails = new ArrayList<IEmail>();

        for(IAccount account : accounts) {
            emails.addAll(account.getEmails());
        }

        return emails;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initFetchingFromAllAccounts() {
        accounts.forEach(IAccount::fetch);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initRefetchingFromAllAccounts() {
        accounts.forEach(IAccount::refetch);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean readAccounts(String filename) {
        IObjectReader<ArrayList<IAccount>> objectReader = new ObjectReader<>();

        try {
            accounts = objectReader.read(filename);
            //Decode each accounts password
            for(IAccount account : accounts) {
                initChipher(Cipher.DECRYPT_MODE, account);
                String encodedPass = account.getPassword();
                System.out.println("Password before decrypt: " + encodedPass);
                String decodedPass = "";
                try {
                    decodedPass = cipher.doFinal(encodedPass.getBytes()).toString();
                    System.out.println("Password after decrypt: " + decodedPass);
                } catch (IllegalBlockSizeException e) {
                    //TODO: Handel
                    e.printStackTrace();
                } catch (BadPaddingException e) {
                    //TODO: Handel
                    e.printStackTrace();
                }
                account.setPassword(decodedPass);
            }
        } catch (ObjectReadException e) {
            // initiate accounts to a new empty ArrayList to make sure that no corrupt data has been loaded
            accounts = new ArrayList<>();
            return false;
        }

        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean writeAccounts(String filename) {


        //Encrypt Password for each account
        for(IAccount account : accounts){
            initChipher(Cipher.ENCRYPT_MODE, account);
            String pass = account.getPassword();
            System.out.println("Password before encrypt: " + pass);
            String encodedPass = "";
            try {
                encodedPass = cipher.doFinal(pass.getBytes()).toString();
                System.out.println("Password after encrypt: " + encodedPass);
            } catch (IllegalBlockSizeException e) {
                //TODO: Handel
                e.printStackTrace();
            } catch (BadPaddingException e) {
                //TODO: Handel
                e.printStackTrace();
            }
            account.setPassword(encodedPass);
        }
        IObjectWriter<ArrayList<IAccount>> objectReaderWriter = new ObjectWriter<>();
        return objectReaderWriter.write((ArrayList<IAccount>) accounts, filename);
    }

    private void initChipher(int operation, IAccount account){
        try {
            cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        } catch (NoSuchAlgorithmException e) {
            //TODO: Handel
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            //TODO: Handel
            e.printStackTrace();
        }

        if(operation == Cipher.DECRYPT_MODE) {
            if (account.getPrivatePasswordKey() != null) {
                try {
                    cipher.init(operation, account.getPrivatePasswordKey());
                } catch (InvalidKeyException e) {
                    //TODO: Handel
                    e.printStackTrace();
                }
            }
        }else if(operation == Cipher.ENCRYPT_MODE){
            if(account.getPublicPasswordKey() != null) {
                try {
                    cipher.init(operation, account.getPublicPasswordKey());
                } catch (InvalidKeyException e) {
                    //TODO: Handel
                    e.printStackTrace();
                }
            }
        }
    }

}
