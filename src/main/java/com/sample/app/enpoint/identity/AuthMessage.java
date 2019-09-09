package com.sample.app.enpoint.echo;

import com.sample.app.client.RemoteClient;
import com.sample.app.configuration.OpenTraceConfig;
import com.sample.app.endpoint.timestamp.Timestamp;
import com.sample.app.filter.client.OpenTraceHttpClientInterceptorBuilder;
import com.sample.app.filter.client.OpenTraceJaxRsClientFilterBuilder;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * A simple echo message, containing the text to be echoed
 * and timestamp of the moment the message was created
 *
 */
@Component
public class EchoMessage {

    private long timestampFromRemote;
    private String echoText;
    Timestamp tStamp;

    @Autowired
    OpenTraceConfig config;


    @Autowired
    Timestamp timestamp;

    @Autowired
    RemoteClient remoteClient;

    @Autowired
    OpenTraceJaxRsClientFilterBuilder openTraceClientFilterBuilder;

    @Autowired
    OpenTraceHttpClientInterceptorBuilder httpClientInterceptorBuilder;

    @PostConstruct
    public void init() {
        tStamp = remoteClient.createClient(Timestamp.class,
                httpClientInterceptorBuilder.getWavefrontHttpRequestClientFilter(),
                httpClientInterceptorBuilder.getWavefrontHttpResponseClientFilter());
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
        timestampFromRemote = tStamp.timestamp();
        String tempToken = null;
        if(config.getTarget() != null && config.getTarget().getToken() != null){
            tempToken = config.getTarget().getToken();
            tempToken = new String(Base64.decodeBase64(tempToken));
            this.echoText = echoText + " " + tempToken;
        }else{
            this.echoText = echoText;
        }
    }

    public String getEchoText(String echoText) {
        return echoText;

    }

}