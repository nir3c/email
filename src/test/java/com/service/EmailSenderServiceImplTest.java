package com.service;

import com.model.Message;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.subethamail.wiser.Wiser;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MailDateFormat;
import javax.mail.internet.MimeMessage;
import java.util.Date;

/**
 * Created by Nir.
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
public class EmailSenderServiceImplTest {

    @Autowired
    private EmailSenderService senderService;
    private Wiser wiser;

    @Before
    public void init(){
        wiser = new Wiser();
        wiser.setPort(25);
        wiser.setHostname("localhost");
        wiser.start();
    }

    @After
    public void finish(){
        wiser.stop();
    }

    @Test
    public void sendMessageTest() throws MessagingException {
        Message message = createMessage();
        senderService.sendMessages(message);
        assertMessage(message, wiser);
    }

    private void assertMessage(Message message, Wiser wiser) throws MessagingException {
        MimeMessage mimeMessage = wiser.getMessages().get(0).getMimeMessage();
        Assert.assertEquals(new InternetAddress(message.getFrom()), mimeMessage.getFrom()[0]);
        Assert.assertEquals(new InternetAddress(message.getTo()[0]),
                mimeMessage.getRecipients(javax.mail.Message.RecipientType.TO)[0]);
        MailDateFormat mailDateFormat = new MailDateFormat();
        Assert.assertEquals(mailDateFormat.format(message.getSentDate()), mailDateFormat.format(mimeMessage.getSentDate()));
        Assert.assertEquals(message.getSubject(), mimeMessage.getSubject());
    }

    private Message createMessage() {
        Message message = new Message();
        message.setSubject("subject");
        message.setText("text");
        message.setSentDate(new Date());
        message.setFrom("from@test.com");
        message.setTo(new String[]{"to@test.com"});
        return message;
    }

}