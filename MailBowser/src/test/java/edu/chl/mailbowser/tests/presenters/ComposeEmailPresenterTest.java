package edu.chl.mailbowser.tests.presenters;

import edu.chl.mailbowser.email.models.Address;
import edu.chl.mailbowser.email.models.IAddress;
import edu.chl.mailbowser.presenters.ComposeEmailPresenter;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by mats on 18/05/15.
 */
public class ComposeEmailPresenterTest {


    /**
     * Tests if ComposeEmailPresenter parses the email addresses correctly
     * @throws Exception
     */
    @Test
    public void testParseAddresses() throws Exception {
        String str = "mats@hgbrg.se, mailbows3r@gmail.com, hej@hotmail.com";
        List<IAddress> parsed = ComposeEmailPresenter.parseAddresses(str);

        //Checks the 3x addresses given
        assertTrue(parsed.contains(new Address("mats@hgbrg.se")));
        assertTrue(parsed.contains(new Address("mailbows3r@gmail.com")));
        assertTrue(parsed.contains(new Address("hej@hotmail.com")));

        //Checks a random address
        assertFalse(parsed.contains(new Address("abc@hej.com")));
    }
}