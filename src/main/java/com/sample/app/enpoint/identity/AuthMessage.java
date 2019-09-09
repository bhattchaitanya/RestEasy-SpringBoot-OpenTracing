package com.sample.app.enpoint.identity;

import com.sample.app.client.routes.ClientWithOpenTraceFilter;
import com.sample.app.opentrace.config.OpenTraceConfig;
import com.sample.app.endpoint.timestamp.Timestamp;
import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * A simple echo message, containing the text to be echoed
 * and timestamp of the moment the message was created
 *
 */
@Component
public class AuthMessage {

    private long timestampFromRemote;
    private String echoText;
    Timestamp timestamp;

    @Autowired
    OpenTraceConfig config;


    @Autowired
    ClientWithOpenTraceFilter clientWithOpenTraceFilter;


    @PostConstruct
    public void init() {
        echoText = this.echoText;
    }

    public long getTimestamp() {
        return timestampFromRemote;
    }

    public void setTimestamp() {
        this.timestamp = timestamp;
    }

    public String getEchoText() {
        return echoText;
    }

    public void setEchoText(String echoText){
        MDC.put("tester-id","mdc-value-echo-IUS-2212");

        timestampFromRemote = clientWithOpenTraceFilter.getTimestampClient().timestamp();
        String tempToken = null;
        if(config.getTarget() != null && config.getTarget().getToken() != null){
            tempToken = config.getTarget().getToken();
            tempToken = new String(Base64.decodeBase64(tempToken));
            this.echoText = echoText + " " + tempToken;
            int i = 0;
            while(i < 0){
                this.echoText = this.echoText + "\n" + "RiskProfile = "
                        +clientWithOpenTraceFilter.getRiskAnalysisClient().riskanalysis();
                i++;
            }

        }else{
            this.echoText = echoText;
        }
    }

    public String getEchoText(String echoText) {
        return echoText;

    }

}