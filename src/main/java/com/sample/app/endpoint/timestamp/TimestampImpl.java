package com.sample.app.endpoint.timestamp;

import org.springframework.stereotype.Component;

/**
 * A simple echo message, containing the text to be echoed
 * and timestamp of the moment the message was created
 *
 */
@Component
public class TimestampImpl implements Timestamp{

    private long timestamp;

    @Override
    public long timestamp() {
        return System.currentTimeMillis();
    }
}