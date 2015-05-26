package edu.chl.mailbowser.tests.email;

import edu.chl.mailbowser.email.Email;
import edu.chl.mailbowser.tests.mock.MockAddress;
import org.junit.Before;
import org.junit.Test;

import javax.mail.Message;
import javax.mail.Session;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by OscarEvertsson on 30/04/15.
 */
public class EmailTest {
    private Email email;

    private String subject = "subject";
    private String content = "content";
    private MockAddress sender;
    private List<MockAddress> to;
    private List<MockAddress> cc;
    private List<MockAddress> bcc;

    @Before
    public void setUp() {
        sender = new MockAddress();
        to = new ArrayList<>();
        cc = new ArrayList<>();
        bcc = new ArrayList<>();
        to.add(new MockAddress());
        cc.add(new MockAddress());
        bcc.add(new MockAddress());
        email = new Email.Builder(subject, content)
                .sender(sender)
                .to(to)
                .cc(cc)
                .bcc(bcc)
                .build();
    }

    @Test
    public void testBuilder() throws Exception {
        // test if all values are set correctly
        assertEquals(email.getSubject(), subject);
        assertEquals(email.getContent(), content);
        assertEquals(email.getSender(), sender);
        assertEquals(email.getTo(), to);
        assertEquals(email.getCc(), cc);
        assertEquals(email.getBcc(), bcc);
    }

    @Test
    public void testGetJavaMailMessageAndConstructor() throws Exception {
        // get a JavaMail message from the email and then transform it to an email again. check if all fields are
        // set correctly
        Session session = Session.getInstance(new Properties()); // JavaMail class
        Message message = email.getJavaMailMessage(session); // JavaMail class
        Email newEmail = new Email(message);

        assertEquals(newEmail.getSubject(), subject);
        assertEquals(newEmail.getContent(), content);

        // tests if the getJavaMailAddress method has been called in the address objects
        assertTrue(sender.getJavaMailAddressCalled);
        assertTrue(to.get(0).getJavaMailAddressCalled);
        assertTrue(cc.get(0).getJavaMailAddressCalled);
        assertTrue(bcc.get(0).getJavaMailAddressCalled);

        // tests if the sent dates and received dates are approximately the same
        assertTrue(newEmail.getSentDate().getTime() - email.getSentDate().getTime() < 100);
        assertTrue(newEmail.getReceivedDate().getTime() - email.getReceivedDate().getTime() < 100);

        // tests if you can get an email from a null JavaMail message
        boolean exceptionThrown = false;
        try {
            new Email(null);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        assertTrue(exceptionThrown);
    }

    @Test
    public void testMatches() throws Exception {
        //Same string but no caps
        assertTrue(email.matches(subject));

        //Same string with all caps
        assertTrue(email.matches(subject.toUpperCase()));

        //Same string but no caps
        assertTrue(email.matches(content));

        //Same string but all caps
        assertTrue(email.matches(content.toUpperCase()));

        // sender, to, cc and bcc
        email.matches("sender");
        email.matches("to");
        email.matches("cc");
        email.matches("bcc");
        assertTrue(sender.matchesCalled);
        assertTrue(to.get(0).matchesCalled);
        assertTrue(cc.get(0).matchesCalled);
        assertTrue(bcc.get(0).matchesCalled);

        //Part of subject
        assertTrue(email.matches("ubje"));

        //Part of content
        assertTrue(email.matches("nten"));

        //Random string
        assertFalse(email.matches("abcdefg"));

        //Random string caps
        assertFalse(email.matches("ABCDEFG"));

        //Null
        assertFalse(email.matches(null));

        //Empty string
        assertTrue(email.matches(""));
    }
}