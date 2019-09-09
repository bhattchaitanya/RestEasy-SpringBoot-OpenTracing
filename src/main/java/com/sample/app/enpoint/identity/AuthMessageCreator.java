package com.sample.app.enpoint.identity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * This bean creates {@link AuthMessage} objects based on
 * echo texts received as input
 *
 */
@Component
public class AuthMessageCreator {

    @Autowired
    AuthMessage message;


    public AuthMessage createEchoMessage(String echoText) {
         message.setEchoText(echoText);
         return message;
    }

}
