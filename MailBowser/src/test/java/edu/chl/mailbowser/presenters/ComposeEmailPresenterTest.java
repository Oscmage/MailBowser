package edu.chl.mailbowser.presenters;

import edu.chl.mailbowser.email.models.Address;
import edu.chl.mailbowser.email.models.IAddress;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by mats on 18/05/15.
 */
public class ComposeEmailPresenterTest {

    @Test
    public void testParseAddresses() throws Exception {
        String str = "mats@hgbrg.se, mailbows3r@gmail.com, hej@hotmail.com";
        List<IAddress> parsed = ComposeEmailPresenter.parseAddresses(str);
        assertTrue(parsed.contains(new Address("mats@hgbrg.se")));
        assertTrue(parsed.contains(new Address("mailbows3r@gmail.com")));
        assertTrue(parsed.contains(new Address("hej@hotmail.com")));
        assertFalse(parsed.contains(new Address("abc@hej.com")));
    }
}