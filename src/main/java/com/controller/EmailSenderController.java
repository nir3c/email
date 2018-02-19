package com.controller;

import com.model.Message;
import com.service.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Nir.
 */
@RestController
public class EmailSenderController {

    EmailSenderService senderService;

    @Autowired
    public EmailSenderController(EmailSenderService senderService) {
        this.senderService = senderService;
    }

    @RequestMapping(path = "/send", method = RequestMethod.POST)
    public void send(@RequestBody Message message){
        senderService.sendMessages(message);
    }

}
