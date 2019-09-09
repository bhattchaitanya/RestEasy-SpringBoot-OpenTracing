package com.sample.app.endpoint.Phone;

import com.sample.app.client.routes.ClientWithOpenTraceFilter;
import com.sample.app.endpoint.timestamp.Timestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * A simple echo message, containing the text to be echoed
 * and timestamp of the moment the message was created
 *
 */
@Component
public class PhoneImpl implements Phone {


    Timestamp tStamp;
    private String phone = "510100000";


    @Autowired
    ClientWithOpenTraceFilter clientWithOpenTraceFilter;


    @Override
    public String phone(){
        clientWithOpenTraceFilter.getAddressClient().getAddress();
        return phone;
    }
}