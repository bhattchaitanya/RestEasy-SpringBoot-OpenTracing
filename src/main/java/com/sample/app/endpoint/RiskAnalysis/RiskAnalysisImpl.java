package com.sample.app.endpoint.RiskAnalysis;

import com.sample.app.client.routes.ClientWithOpenTraceFilter;
import com.sample.app.endpoint.address.Address;
import org.apache.log4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * A simple echo message, containing the text to be echoed
 * and timestamp of the moment the message was created
 *
 */
@Component
public class RiskAnalysisImpl implements RiskAnalysis {

    private String riskProfile = "LOW_RISK";


    @Autowired
    ClientWithOpenTraceFilter clientWithOpenTraceFilter;

    @Override
    public String riskanalysis() {

        MDC.put("tester-id","mdc-value-112-risk");

        try{

            Thread.sleep(new Random().nextInt(100));
        }catch (Exception e){

        }

        Random rand = new Random();
        return riskProfile + rand.nextInt();
    }
}