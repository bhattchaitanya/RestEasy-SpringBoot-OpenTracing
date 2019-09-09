package com.sample.app.endpoint.Phone;

import com.sample.app.endpoint.address.Address;
import org.springframework.stereotype.Component;

/**
 * A simple echo message, containing the text to be echoed
 * and timestamp of the moment the message was created
 *
 */
@Component
public class PhoneImpl implements Address {

    private long timestamp;

    @Override
    public long timestamp() {
        return System.currentTimeMillis();
    }
}