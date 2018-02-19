package com.service;

import com.model.Message;

/**
 * Created by Nir.
 */
public interface EmailSenderService {

    void sendMessages(Message... messages);
}
