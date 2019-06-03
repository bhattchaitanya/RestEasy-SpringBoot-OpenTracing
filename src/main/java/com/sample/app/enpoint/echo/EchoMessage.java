package com.sample.app.enpoint.echo;

/**
 * A simple echo message, containing the text to be echoed
 * and timestamp of the moment the message was created
 *
 * @author Fabio Carvalho (facarvalho@paypal.com or fabiocarvalho777@gmail.com)
 */
public class EchoMessage {

    private long timestamp;
    private String echoText;

    public EchoMessage(String echoText) {
        timestamp = System.currentTimeMillis();
        this.echoText = echoText+"blah";
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getEchoText() {
        return echoText;
    }

}