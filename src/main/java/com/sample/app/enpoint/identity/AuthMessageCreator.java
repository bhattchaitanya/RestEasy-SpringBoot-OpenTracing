package com.sample.app.enpoint.echo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * This bean creates {@link EchoMessage} objects based on
 * echo texts received as input
 *
 */
@Component
public class EchoMessageCreator {

    @Autowired
    EchoMessage message;


    public EchoMessage createEchoMessage(String echoText) {
         message.setEchoText(echoText);
         return message;
    }

}
