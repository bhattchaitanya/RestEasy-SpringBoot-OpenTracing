package com.sample.app.endpoint.address;

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
public class AddressImpl implements Address {

    private String address;

    Timestamp tStamp;

    @Autowired
    ClientWithOpenTraceFilter clientWithOpenTraceFilter;


    @Override
    public String getAddress() {
        clientWithOpenTraceFilter.getRiskAnalysisClient().riskanalysis();
        return "7535 Torrey Santa Fe, CA - 92078";
    }
}