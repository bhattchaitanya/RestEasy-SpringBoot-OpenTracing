package com.sample.app.client.routes;

import com.sample.app.client.RemoteClient;
import com.sample.app.configuration.FilterConfig;
import com.sample.app.opentrace.config.OpenTraceConfig;
import com.sample.app.endpoint.Phone.Phone;
import com.sample.app.endpoint.RiskAnalysis.RiskAnalysis;
import com.sample.app.endpoint.address.Address;
import com.sample.app.endpoint.timestamp.Timestamp;
import com.sample.app.opentrace.filter.client.OpenTraceHttpClientInterceptorBuilder;
import com.sample.app.opentrace.filter.client.OpenTraceJaxRsClientFilterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ClientWithOpenTraceFilter {

    private long timestampFromRemote;
    private String echoText;

    @Autowired
    FilterConfig filterConfig;

    @Autowired
    OpenTraceConfig config;

    @Autowired
    Timestamp timestamp;

    @Autowired
    Phone phone;

    @Autowired
    Address address;

    @Autowired
    RiskAnalysis riskAnalysis;

    @Autowired
    RemoteClient remoteClient;

    OpenTraceJaxRsClientFilterBuilder openTraceClientFilterBuilder = new OpenTraceJaxRsClientFilterBuilder();

    OpenTraceHttpClientInterceptorBuilder httpClientInterceptorBuilder = new OpenTraceHttpClientInterceptorBuilder();

    @PostConstruct
    public void init() {

        if ( filterConfig.isApacheHttpClient()) {

            timestamp = remoteClient.createClient(Timestamp.class,
                    "http://localhost:8090/ius/sample-app/",
                    httpClientInterceptorBuilder.getWavefrontHttpRequestClientFilter(),
                    httpClientInterceptorBuilder.getWavefrontHttpResponseClientFilter());


            riskAnalysis = remoteClient.createClient(RiskAnalysis.class,
                    "http://localhost:8089/ius/sample-app/",
                    httpClientInterceptorBuilder.getWavefrontHttpRequestClientFilter(),
                    httpClientInterceptorBuilder.getWavefrontHttpResponseClientFilter());

        }else{
            timestamp = remoteClient.createClient(Timestamp.class,
                    "http://localhost:8090/ius/sample-app/",
                    openTraceClientFilterBuilder.getWavefrontJaxrsClientFilter());

            riskAnalysis = remoteClient.createClient(RiskAnalysis.class,
                    "http://localhost:8089/ius/sample-app/",
                    openTraceClientFilterBuilder.getWavefrontJaxrsClientFilter());

        }

    }

    public Timestamp getTimestampClient() {
        return timestamp;
    }

    public Phone getPhoneClient() {
        return phone;
    }

    public Address getAddressClient() {
        return address;
    }

    public RiskAnalysis getRiskAnalysisClient() {
        return riskAnalysis;
    }
}
