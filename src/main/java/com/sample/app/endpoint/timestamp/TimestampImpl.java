package com.sample.app.endpoint.timestamp;

import com.sample.app.client.routes.ClientWithOpenTraceFilter;
import org.apache.log4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * A simple echo message, containing the text to be echoed
 * and timestamp of the moment the message was created
 */
@Component
public class TimestampImpl implements Timestamp {

    private long timestamp;

    @Autowired
    ClientWithOpenTraceFilter clientWithOpenTraceFilter;


    @Override
    public long timestamp() {
        clientWithOpenTraceFilter.getRiskAnalysisClient().riskanalysis();
        MDC.put("tester-id","mdc-value-321");
        int i = 0;
        int n = 50;
        while( i < n){
            i++;
            MDC.put("tester-id"+i,"mdc-value-32"+i);
        }

        MDC.put("intuit_context", "external_risk_score=REVIEW rs_trust_tag=captcha_initiated auth_result_detail=[internal_risk_score=null,external_risk_score=REVIEW,captcha_required=true,authenticator=PASSWORD] risk_level=MED action=CHALLENGE CAPTCHA=on CARE=on" );
        return System.currentTimeMillis();
    }
}