package com.service;

import com.model.Message;
import com.properties.MimeMessageProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by Nir.
 */
@Service
public class EmailSenderServiceImpl implements EmailSenderService{

    private JavaMailSender sender;
    private static Logger logger = LoggerFactory.getLogger(EmailSenderServiceImpl.class);
    private MimeMessageProperties properties;

    @Autowired
    public EmailSenderServiceImpl(JavaMailSender sender, MimeMessageProperties properties) {
        this.sender = sender;
        this.properties = properties;
    }

    @Override
    public void sendMessages(Message... messages) {
        List<MimeMessage> mimeMessages =
                Arrays.stream(messages)
                        .map(m -> prepareMimeMessageForSend(sender.createMimeMessage(), properties, m))
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList());
        sendSMTPMessages(mimeMessages);
    }

    private static MimeMessage prepareMimeMessageForSend(MimeMessage mimeMessage, MimeMessageProperties properties, Message message) {
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, properties.getMultipartMode());
            helper.setFrom(message.getFrom());
            helper.setTo(message.getTo());
            helper.setBcc(message.getBcc());
            helper.setCc(message.getCc());
            helper.setSubject(message.getSubject());
            helper.setText(message.getText(), properties.isTextHtmlContentType());
            helper.setSentDate(message.getSentDate());
            addHeaders(helper, message);
            addAttachments(helper, message);
            return helper.getMimeMessage();
        } catch (Exception e){
            logger.error("Failed generate MimeMessage", e);
        }
        return null;
    }

    private static void addAttachments(MimeMessageHelper helper, Message message) throws MessagingException {
        File file;
        for (String filePath: message.getAttachmentsPaths()) {
            file = new File(filePath);
            helper.addAttachment(file.getName(), file);
        }
    }

    private static void addHeaders(MimeMessageHelper helper, Message message) throws MessagingException {
        for (Map.Entry<String, String> header : message.getHeaders().entrySet())
            helper.getMimeMessage().setHeader(header.getKey(), header.getValue());
    }

    private void sendSMTPMessages(List<MimeMessage> messages){
        try {
            if(!CollectionUtils.isEmpty(messages)) {
                sender.send(messages.toArray(new MimeMessage[messages.size()]));
                logger.info("send successfuly messages");
            }
        } catch (Exception e){
            logger.error("Failed Sending Messages", e);
        }
    }
}
